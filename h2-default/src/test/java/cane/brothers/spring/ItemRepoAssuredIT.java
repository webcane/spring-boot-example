package cane.brothers.spring;

import java.util.Arrays;

import org.apache.http.HttpStatus;

import org.hamcrest.Matchers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import cane.brothers.spring.model.Item;
import cane.brothers.spring.repo.ItemRepository;

@RunWith(SpringJUnit4ClassRunner.class) // to create spring application context
@SpringApplicationConfiguration(classes = BootApplication.class)
@WebAppConfiguration // to load WebApplicationContext
@IntegrationTest("server.port:0") // to start embedded web server
public class ItemRepoAssuredIT {

	@Value("${local.server.port}")
    int port;
	
	@Autowired
	ItemRepository repository;
	
	Item testItem;
	Item testItem4;
	Item editedItem;
	
	@Before
    public void setUp() {
		testItem = new Item("test", "test item");
		testItem4 = new Item("test4", "test item 4");
		editedItem = new Item("item3", "item 3 descr");

        repository.deleteAll();
        repository.save(Arrays.asList(testItem));
        
        RestAssured.port = port;
    }

	@Test
	public void canFetchAll() {
		RestAssured
			.when().get("/items")
			.then().statusCode(HttpStatus.SC_OK)
			.body("_embedded.items", Matchers.hasSize(1));
	}
	
	@Test
	public void canFetchById_ShouldReturnItem() {	
		RestAssured
			//expect().body("name", Matchers.equalTo("test"))
			.when().get("/items/{id}", Long.valueOf(3L))
			.then().statusCode(HttpStatus.SC_OK)
			.body("name", Matchers.equalTo("test"));
	}
	
	@Test
	public void canFetchByWrongId_ShouldNotFound() {
		RestAssured
			.expect().statusCode(HttpStatus.SC_NOT_FOUND)
			.when().get("/items/{id}", Long.valueOf(1000L));
	}
	
	@Test
	public void canAddItem_ShouldReturnSavedItem() {
		RestAssured
			.given().body(testItem4).and()
			.contentType(ContentType.JSON)
			.when().post("/items")
			.then().statusCode(HttpStatus.SC_CREATED)
			.body("name", Matchers.is("test4"));
	}
	
	@Test
	public void cannotAddItemWithoutBody_ShouldReturnBadRequest() {
		RestAssured
			.when().post("/items")
			.then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	//public void cannotAddItemWithoutContentType_ShouldReturnNotSupportedMediaType() {
	public void cannotAddItemWithoutContentType_ShouldReturnBadRequest() {
		RestAssured
		.given().body(testItem4).and()
		.when().post("/items")
		//.then().statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
		.then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void canUpdateItem_ShouldReturnChangedItem() {
		RestAssured
			.given().contentType(ContentType.JSON).and()
			.body(editedItem)
			.when().put("/items/{id}", Long.valueOf(3L))
			.then().statusCode(HttpStatus.SC_CREATED)
			.body("name", Matchers.is("item3"));
	}
	
	@Test
	public void cannotUpdateItemWithoutBody_ShouldReturnBadRequest() {
		RestAssured
			.when().put("/items/{id}", Long.valueOf(3L))
			.then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	//public void cannotUpdateItemWithoutContentType_ShouldReturnNotSupportedMediaType() {
	public void cannotUpdateItemWithoutContentType_ShouldReturnBadRequest() {
		RestAssured
			.given().body(editedItem)
			.when().put("/items/{id}", Long.valueOf(3L))
			//.then().statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
			.then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void canFetchByName_ShouldReturnItem() {
		RestAssured
			.when().get("/items/search/findByName?name={name}", "test")
			.then().statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void cannotFetchByWrongName_ShouldReturnEmptyItems() {
		RestAssured
			.when().get("/items/search/findByName?name={name}", "WrongItemPitem")
			.then().statusCode(HttpStatus.SC_OK)
			.body("_embedded.items", Matchers.hasSize(0));
	}
}
