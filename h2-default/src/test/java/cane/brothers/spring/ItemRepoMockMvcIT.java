package cane.brothers.spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cane.brothers.spring.model.Item;
import cane.brothers.spring.repo.ItemRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BootApplication.class)
@WebAppConfiguration
public class ItemRepoMockMvcIT {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			"hal+json");

	private MockMvc mockMvc;
	
	@Autowired
	ItemRepository repository;
	
	Item testItem;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		// инициализация проходит каждый раз перед @Test
		testItem = new Item("test", "test item");
		
        repository.deleteAll();
        testItem = repository.save(testItem);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void canFindAll_ShouldReturnItems() throws Exception {
		mockMvc.perform(get("/items"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8));
			//.andExpect(jsonPath("$._embedded.items.length()").value(1));
			//.andDo(print());
	}
	
	@Test
    public void canFindOne_ShouldReturnItem() throws Exception {
		//not working
		mockMvc.perform(get("/items/{id}", testItem.getId()))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.name").value("test"));
    }
	
	@Test
    public void canNotFindOne_ShouldReturnItem() throws Exception {
        mockMvc.perform(get("/items/1000"))
        		.andExpect(status().isNotFound());
    }
}
