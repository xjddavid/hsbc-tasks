import re
import requests
import json
import datetime

ERROR = "ERROR"
VALID = "VALID"
STOP = "STOP"
OPERATIONS = ["add", "done", "delete", "list"]
URL = "http://localhost:80/api/tasks"
SUCCESS_CODE = 200


# get the valid query date string with from and to format
def get_day_query(date):
    current_day_str = date.__format__('%d%m%Y')
    next_day = date + datetime.timedelta(days=1)
    next_day_str = next_day.__format__('%d%m%Y')
    return "?from=" + current_day_str + "&to=" + next_day_str


def get_list_params(word):
    # Backend support more complex search. The front end only supports one day search
    if not re.match("^--expiring-", word):
        return ERROR, "Expiring parameter wrong. Valid Examples are --expiring-today, --expiring-12/01/2020"
    words = word.split("-")
    if len(words) != 4:
        return ERROR, "Expiring parameter wrong. Valid Examples are --expiring-today, --expiring-12/01/2020"
    if words[-1] == "today":
        current_day = datetime.date.today()
        return VALID, get_day_query(current_day)
    elif words[-1] == "tomorrow":
        current_day = datetime.date.today() + datetime.timedelta(days=1)
        return VALID, get_day_query(current_day)
    elif re.match("^[\d]{2}/[\d]{2}/[\d]{4}$", words[-1]):
        date = words[-1].split("/")
        try:
            current_day = datetime.date(int(date[2]), int(date[1]), int(date[0]))
            return VALID, get_day_query(current_day)
        except ValueError as e:
            return ERROR, e.__str__()

    else:
        return ERROR, "Expiring parameter wrong. Valid Examples are --expiring-today, --expiring-12/01/2020"


def get_add_params(words):
    add_json = {}
    if len(words) == 1:
        return ERROR, "Invalid input"
    if words[0][0] != '"' or words[-2][-1] != '"':
        return ERROR, "Title should between quotation marks"
    if not re.match("^[\d]{2}/[\d]{2}/[\d]{4}$", words[-1]):
        return ERROR, "Date format should be dd/MM/yyyy"
    title = " ".join(words[:-1])[1:-1]
    if title == "" or title == " ":
        return ERROR, "Title cannot be empty string"
    add_json["title"] = title
    add_json["dueDate"] = "".join(words[-1].split('/'))
    return VALID, add_json


def parse_input(s):
    if s is None:
        return "No input!"
    if s == "stop":
        return STOP
    words = s.split()
    if len(words) < 2:
        return "Invalid input"
    if words[0] != "tasks":
        return "Invalid input"
    if words[1] not in OPERATIONS:
        return "Invalid operation, the second word must be 'add', 'done', 'delete' or 'list'."

    if words[1] == "add":
        valid, ret = get_add_params(words[2:])
        if valid == ERROR:
            return ret
        response = requests.post(url=URL, json=ret)
        return response.text

    if words[1] == "done":
        if len(words[2:]) != 1:
            return "Invalid input, done operation should provide only task id."
        response = requests.patch(url=URL + "/" + words[2], json={"status": "DONE"})
        return response.text

    if words[1] == "delete":
        if len(words[2:]) != 1:
            return "Invalid input, delete operation should provide only task id."
        response = requests.delete(url=URL + "/" + words[2])
        if response.status_code == SUCCESS_CODE:
            return "Delete operation success."
        return response.text

    if words[1] == "list":
        # get all tasks
        if len(words) == 2:
            response = requests.get(url=URL)
            if response.status_code == SUCCESS_CODE:
                tasks = json.loads(response.text)
                return "\n".join([str(t) for t in tasks])
            return response.text
        if len(words) == 3:
            valid, ret = get_list_params(words[-1])
            if valid == ERROR:
                return ret
            response = requests.get(url=URL + ret)
            if response.status_code == SUCCESS_CODE:
                tasks = json.loads(response.text)
                return "\n".join([str(t) for t in tasks])
            return response.text


if __name__ == '__main__':
    # s1 = "tasks add \"\" 21/08/2019"
    # s2 = "tasks done 4"
    # s3 = "tasks delete 4"
    # s4 = "tasks list"
    # s5 = "tasks list --expiring-today"
    # s6 = "tasks list --expiring-tomorrow"
    # s7 = "tasks list --expiring-12/01/1987"
    #
    # parse_input(s7)
    while True:
        command = input()
        response = parse_input(command)
        if response == STOP:
            break
        print(response)
        with open('operation.log', 'a') as f:
            f.write(datetime.datetime.now().__str__() + '\t' + command + '\n')
            f.write(response + '\n')
