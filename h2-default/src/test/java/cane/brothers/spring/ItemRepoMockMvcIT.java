package cane.brothers.spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
//import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import cane.brothers.spring.model.Item;
import cane.brothers.spring.repo.ItemRepository;

/**
 * Test REST resource repository with MockMvc
 * 
 * @author Mikhail Niedre
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BootApplication.class)
@WebAppConfiguration
public class ItemRepoMockMvcIT {

	private MockMvc mockMvc;
	
	@Autowired
	ItemRepository repository;
	
	Item testItem;
	Item testItem4 = new Item("test4", "test item 4");

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.setSerializationInclusion(JsonSerialize.Include);
        return mapper.writeValueAsBytes(object);
    }

	/**
	 * do setup before any @Test
	 */
	@Before
	public void setUp() {
		testItem = new Item("test", "test item");
		
        repository.deleteAll();
        testItem = repository.save(testItem);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void canFindAll_ShouldReturnItems() throws Exception {
		mockMvc.perform(get("/items"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaTypes.HAL_JSON));
			//.andExpect(jsonPath("$._embedded.items.length()").value(1));
			//.andDo(print());
	}
	
	@Test
    public void canFindOne_ShouldReturnItem() throws Exception {
		mockMvc.perform(get("/items/{id}", testItem.getId()))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.name").value("test"));
    }
	
	@Test
    public void canNotFindOne_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/items/1000"))
        		.andExpect(status().isNotFound());
    }
	
	@Test
	public void canAddItem_ShouldReturnSavedItem() throws Exception {
		mockMvc.perform(post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(testItem4)))
		.andExpect(status().isCreated());
		// FIXME content is empty
		//.andExpect(jsonPath("name").value("test4"));
	}
	
	@Test
	public void canNotAddItemWithoutBody_ShouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/items"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void canNotAddItemWithoutContentType_ShouldReturnBadRequest() throws Exception {
		// TODO we can create without content type
		mockMvc.perform(post("/items")
				.content(convertObjectToJsonBytes(testItem4)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void canEditItem_ShouldReturnChangedItem() throws Exception {
		mockMvc.perform(put("/items/{id}", testItem.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(testItem4)))
		// TODO 204: No Content
		.andExpect(status().isCreated());
		// FIXME content is empty
		//.andExpect(jsonPath("name").value("test4"));
	}
}
