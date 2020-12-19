package cane.brothers.spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
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
@WebAppConfiguration
@ContextConfiguration(classes = BootApplication.class)
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
	@BeforeEach
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
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
			//.andExpect(jsonPath("$._embedded.items.length()").value(1));
			.andDo(log());
	}
	
	@Test
    public void canFindOne_ShouldReturnItem() throws Exception {
		mockMvc.perform(get("/items/{id}", testItem.getId()))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.name").value("test"))
    			.andDo(log());
    }
	
	@Test
    public void canNotFindOne_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/items/1000"))
        		.andExpect(status().isNotFound())
    			.andDo(log());
    }
	
	@Test
	public void canAddItem_ShouldReturnSavedItem() throws Exception {
		mockMvc.perform(post("/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(testItem4)))
		.andExpect(status().isCreated())
		.andDo(log());
		// FIXME content is empty
		//.andExpect(jsonPath("name").value("test4"));
	}
	
	@Test
	public void canNotAddItemWithoutBody_ShouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/items"))
		.andExpect(status().isBadRequest())
		.andDo(log());
	}
	
	@Test
	public void canNotAddItemWithoutContentType_ShouldReturnBadRequest() throws Exception {
		// TODO we can create without content type
		mockMvc.perform(post("/items")
				.content(convertObjectToJsonBytes(testItem4)))
		// TODO 201: isCreated
		.andExpect(status().isBadRequest())
		.andDo(log());
	}
	
	@Test
	public void canEditItem_ShouldReturnChangedItem() throws Exception {
		mockMvc.perform(put("/items/{id}", testItem.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(testItem4)))
		// 200 or 204
		.andExpect(status().is2xxSuccessful())
		.andDo(log());
		// FIXME content is empty
		//.andExpect(jsonPath("name").value("test4"));
	}
	
	@Test
	public void canNotEditItemWithoutBody_ShouldReturnBadRequest() throws Exception {
		mockMvc.perform(put("/items/{id}", testItem4.getId())
				.contentType(MediaType.APPLICATION_JSON))
		// 400 Bad Request or  405 isMethodNotAllowed
		.andExpect(status().is4xxClientError())
		.andDo(log());
	}
	
	@Test
	public void canNotEditItemWithoutContentType_ShouldBadRequest() throws Exception {
		mockMvc.perform(put("/items/{id}", testItem4.getId())
				.content(convertObjectToJsonBytes(testItem4)))
		// 400 Bad Request or  405 isMethodNotAllowed
		.andExpect(status().is4xxClientError())
		.andDo(log());
	}
	
	@Test
	public void canSearchByName_ShouldReturnItem() throws Exception {
		mockMvc.perform(get("/items/search/findByName?name={name}", testItem.getName())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(log());
		// TODO check expected name
	}
	
	@Test
	public void canNotSearchByWrongName_ShouldReturnEmptyItems() throws Exception {
		mockMvc.perform(get("/items/search/findByName?name={name}", "WrongItemPitem")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$._embedded.items").isEmpty());
	}
	
	@Test
	public void canDelete_ShouldDeleteItem() throws Exception {
		mockMvc.perform(delete("/items/{id}", testItem.getId()))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void canNotDeleteByWrongId_ShouldReturnNotBound() throws Exception {
		mockMvc.perform(delete("/items/{id}", 1000L))
		.andExpect(status().isNotFound());
	}	
}
