package com.jiang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.tasks.Constants;
import com.jiang.tasks.MainApplication;
import com.jiang.tasks.domain.Task;
import com.jiang.tasks.dto.TaskUpdateDto;
import com.jiang.tasks.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {
    private static final ObjectMapper om = new ObjectMapper();
    private static final String rootUrl = "http://localhost:8080/api/tasks";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository mockRepository;

    @Before
    public void init() {
        Task task = new Task(1L, "Good task", Constants.convertStringToDate("12122019"), "CREATED");
        when(mockRepository.findById(1L)).thenReturn(Optional.of(task));
    }

    @Test
    public void findTaskById() throws Exception {

        mockMvc.perform(get(rootUrl + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Good task")))
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.dueDate", is("12/12/2019")));

        verify(mockRepository, times(1)).findById(1L);

    }

//    @Test
//    public void find_all_Task_OK() throws Exception {
//
//        List<Task> tasks = Arrays.asList(
//                new Task(1L, "Task A", Constants.convertStringToDate("12122019"), "DONE"),
//                new Task(2L, "Task B", Constants.convertStringToDate("12122019"), "CREATED"));
//        TaskQueryDto taskQueryDto = new TaskQueryDto();
//        when(mockRepository.findAll(TaskRepositorySpec.getSpec(taskQueryDto))).thenReturn(tasks);
//
//        mockMvc.perform(get(rootUrl))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].title", is("Task A")))
//                .andExpect(jsonPath("$[0].dueDate", is("12/12/2019")))
//                .andExpect(jsonPath("$[0].status", is("")))
//                .andExpect(jsonPath("$[1].id", is("DONE")))
//                .andExpect(jsonPath("$[1].title", is("Task B")))
//                .andExpect(jsonPath("$[1].dueDate", is("12/12/2019")))
//                .andExpect(jsonPath("$[1].status", is("CREATED")));
//
//        verify(mockRepository, times(1)).findAll();
//    }

    @Test
    public void findTaskByIdNotFound_404() throws Exception {
        mockMvc.perform(get(rootUrl + "/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find task 2"));
    }

    @Test
    public void saveTaskOk() throws Exception {

        Task newTask = new Task(1L, "Spring Boot Guide", Constants.convertStringToDate("12122019"), "CREATED");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("dueDate", "12122019");
        requestBody.put("title", "Spring Boot Guide");
        when(mockRepository.save(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post(rootUrl)
                .content(om.writeValueAsString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Spring Boot Guide")))
                .andExpect(jsonPath("$.dueDate", is("12/12/2019")))
                .andExpect(jsonPath("$.status", is("CREATED")));

        verify(mockRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void addNewTaskWithNotValidTitle() throws Exception {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("dueDate", "12122019");
        requestBody.put("title", "   ");
        mockMvc.perform(post(rootUrl)
                .content(om.writeValueAsString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Title must be a valid string"));

        verify(mockRepository, times(0)).save(any(Task.class));
    }

    @Test
    public void updateTaskOk() throws Exception {

        Task newTask = new Task(1L, "Spring Boot Guide", Constants.convertStringToDate("12122019"), "CREATED");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("dueDate", "12122019");
        requestBody.put("title", "Spring Boot Guide");
        requestBody.put("status", "CREATED");
        when(mockRepository.save(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(put(rootUrl + "/1")
                .content(om.writeValueAsString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Spring Boot Guide")))
                .andExpect(jsonPath("$.dueDate", is("12/12/2019")))
                .andExpect(jsonPath("$.status", is("CREATED")));

        verify(mockRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void updateNotExistTask() throws Exception {
        TaskUpdateDto taskUpdateDto = new TaskUpdateDto();
        taskUpdateDto.setTitle("Spring Boot Guide");
        mockMvc.perform(put(rootUrl + "/2")
                .content(om.writeValueAsString(taskUpdateDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find task 2"));

        verify(mockRepository, times(0)).save(any(Task.class));
    }

    @Test
    public void updateTaskWithNotValidStatus() throws Exception {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("dueDate", "12345678");
        requestBody.put("title", "hehe");
        requestBody.put("status", "hehe");
        mockMvc.perform(put(rootUrl + "/2")
                .content(om.writeValueAsString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Not valid request"));

        verify(mockRepository, times(0)).save(any(Task.class));
    }

    @Test
    public void patchTaskWithNotValidStatus() throws Exception {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("status", "hehe");
        mockMvc.perform(patch(rootUrl + "/1")
                .content(om.writeValueAsString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("hehe is not a valid status"));

        verify(mockRepository, times(0)).save(any(Task.class));
    }

    @Test
    public void updateTaskWithNotValidDate() throws Exception {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("dueDate", "123456789");
        requestBody.put("title", "hehe");
        requestBody.put("status", "CREATED");
        mockMvc.perform(put(rootUrl + "/1")
                .content(om.writeValueAsString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Cannot parse the date 123456789. Valid date format is DDMMYYYY."));

        verify(mockRepository, times(0)).save(any(Task.class));
    }


    @Test
    public void deleteTaskOK() throws Exception {

        doNothing().when(mockRepository).deleteById(1L);

        mockMvc.perform(delete(rootUrl + "/1"))
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).deleteById(1L);
    }

    @Test
    public void patchTaskOk() throws Exception {

        Task newTask = new Task(1L, "Spring Boot Guide", Constants.convertStringToDate("12122019"), "CREATED");
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("dueDate", "12122019");
        when(mockRepository.save(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(patch(rootUrl + "/1")
                .content(om.writeValueAsString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Spring Boot Guide")))
                .andExpect(jsonPath("$.dueDate", is("12/12/2019")))
                .andExpect(jsonPath("$.status", is("CREATED")));

        verify(mockRepository, times(1)).save(any(Task.class));
    }

}
