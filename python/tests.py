from unittest import TestCase, main, mock

from ternimal_input import *

import json


ERROR_CODE = 404


class MockResponse:
    def __init__(self, text, status_code):
        self.text = text
        self.status_code = status_code

# This method will be used by the mock to replace requests.get
def mocked_requests_get(*args, **kwargs):
    if kwargs["url"] == URL:
        return MockResponse(json.dumps([{"key1": "value1"}]), SUCCESS_CODE)
    elif kwargs["url"] == URL + "?from=11111987&to=12111987":
        return MockResponse(json.dumps([{"key2": "value2"}]), SUCCESS_CODE)

    return MockResponse("Oops", ERROR_CODE)


# This method will be used by the mock to replace requests.delete
def mocked_requests_delete(*args, **kwargs):
    if kwargs["url"] == URL+"/1":
        return MockResponse(None, SUCCESS_CODE)

    return MockResponse("Oops", ERROR_CODE)


# This method will be used by the mock to replace requests.patch
def mocked_requests_patch(*args, **kwargs):
    if kwargs["url"] == URL+"/1" and kwargs["json"] == {'status': 'DONE'}:
        return MockResponse(json.dumps({"key1": "value1"}), SUCCESS_CODE)

    return MockResponse("Oops", ERROR_CODE)



class Tests(TestCase):
    def test_get_add_params(self):
        # number of parameters less than 2
        self.assertEqual(get_add_params(["tasks"]), (ERROR, "Invalid input"))
        # wrong title inputs
        self.assertEqual(get_add_params(["tasks", "hehe", "haha"]), (ERROR, "Title should between quotation marks"))
        self.assertEqual(get_add_params(['"tasks', "hehe", "haha"]), (ERROR, "Title should between quotation marks"))
        # right title worng date
        self.assertEqual(get_add_params(['"tasks', 'hehe"', "haha"]), (ERROR, 'Date format should be dd/MM/yyyy'))
        self.assertEqual(get_add_params(['"tasks', 'hehe"', "12345678"]), (ERROR, 'Date format should be dd/MM/yyyy'))
        # valid input
        self.assertEqual(get_add_params(['"tasks', 'hehe"', "12/34/5678"]), ('VALID', {'dueDate': '12345678', 'title': 'tasks hehe'}))
        # title is empty string
        self.assertEqual(get_add_params(['"', '"', "12/34/5678"]), ('ERROR', 'Title cannot be empty string'))


    def test_get_list_params(self):
        # wrong query format
        self.assertEqual(get_list_params("tasks"), (ERROR, "Expiring parameter wrong. Valid Examples are --expiring-today, --expiring-12/01/2020"))
        self.assertEqual(get_list_params("--expiring-hehe"), (ERROR, "Expiring parameter wrong. Valid Examples are --expiring-today, --expiring-12/01/2020"))
        self.assertEqual(get_list_params("--expiring-"), (ERROR, "Expiring parameter wrong. Valid Examples are --expiring-today, --expiring-12/01/2020"))
        # valid format
        self.assertEqual(get_list_params("--expiring-today")[0], VALID)
        self.assertEqual(get_list_params("--expiring-tomorrow")[0], VALID)
        self.assertEqual(get_list_params("--expiring-12/04/2019")[0], VALID)
        # invalid query date
        self.assertEqual(get_list_params("--expiring-12/34/5678"), (ERROR, "month must be in 1..12"))
        self.assertEqual(get_list_params("--expiring-33/12/5678"), (ERROR, 'day is out of range for month'))


    def test_parse_input(self):
        self.assertEqual(parse_input(None), "No input!")
        self.assertEqual(parse_input("No input!"), "Invalid input")
        self.assertEqual(parse_input("tasks"), "Invalid input")
        self.assertEqual(parse_input("tasks hehe"), "Invalid operation, the second word must be 'add', 'done', 'delete' or 'list'.")

        self.assertEqual(parse_input("tasks add hehe"), "Invalid input")

    @mock.patch('requests.get', side_effect=mocked_requests_get)
    def test_parse_input_get_request(self, mock_get):
        # valid query
        self.assertEqual(parse_input("tasks list"), "{'key1': 'value1'}")
        self.assertEqual(parse_input("tasks list --expiring-11/11/1987"), "{'key2': 'value2'}")
        # invalid query
        self.assertEqual(parse_input("tasks list --expiring-hehe"), "Expiring parameter wrong. Valid Examples are --expiring-today, --expiring-12/01/2020")
        # error response status code
        self.assertEqual(parse_input("tasks list --expiring-11/11/1988"), "Oops")

    @mock.patch('requests.delete',side_effect=mocked_requests_delete )
    def test_parse_input_delete_request(self, mock_delete):
        # invalid query
        self.assertEqual(parse_input("tasks delete hehe haha"), "Invalid input, delete operation should provide only task id.")
        # valid query
        self.assertEqual(parse_input("tasks delete 1"), "Delete operation success.")
        # error response status code
        self.assertEqual(parse_input("tasks delete 2"), "Oops")

    @mock.patch('requests.patch',side_effect=mocked_requests_patch )
    def test_parse_input_patch_request(self, mock_patch):
        # invalid query
        self.assertEqual(parse_input("tasks done hehe haha"), "Invalid input, done operation should provide only task id.")
        # valid query
        self.assertEqual(parse_input("tasks done 1"), '{"key1": "value1"}')
        # error response status code
        self.assertEqual(parse_input("tasks done 2"), "Oops")

main()