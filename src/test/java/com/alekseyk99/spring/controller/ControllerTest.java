package com.alekseyk99.spring.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.alekseyk99.spring.MvcConfig;
import com.alekseyk99.spring.configuration.TestConfiguration;
import com.alekseyk99.spring.dto.NoteDto;
import com.alekseyk99.spring.service.NoteService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MvcConfig.class, TestConfiguration.class })
@WebAppConfiguration
public class ControllerTest {

    private MockMvc mockMvc;
    
    //@Autowired
    //private WebApplicationContext wac;
    
    @Mock
    private NoteService noteServiceMock;
    
    @InjectMocks
    private WebController controller;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
	@Test
	public void testGetAllNotes() throws Exception {
	    
	    NoteDto note1 = new NoteDto().setId(1).setSubject("subject1").setText("text1 text2");
        NoteDto note2 = new NoteDto().setId(2).setSubject("subject2").setText("text3 text4");
	    when(noteServiceMock.getAllList()).thenReturn(Arrays.asList(note1, note2));
	    
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("notes"))
            //.andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"))
            .andExpect(model().attribute("listNote", hasSize(2)))
            .andExpect(model().attribute("listNote", contains(note1, note2)));

        verify(noteServiceMock, times(1)).getAllList();
        verifyNoMoreInteractions(noteServiceMock);
	    
	}

}

