import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AmazonEgTest {
    WebDriver driver;

    // Setup method to initialize WebDriver and navigate to Amazon Egypt
    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.eg");
    }

    // Method to perform search by entering the keyword and submitting
    private void performSearch(String keyword) {
        WebElement searchBar = driver.findElement(By.id("twotabsearchtextbox"));
        searchBar.clear();
        searchBar.sendKeys(keyword);
        searchBar.submit();
    }

    // Test Case 1: Search with filters for price and brand
    @Test
    public void testSearchWithPriceAndBrandFilter() {
        performSearch("laptop");

        // Applying price filter (example)
        WebElement priceFilter = driver.findElement(By.xpath("//span[text()='Price: 5,000 EGP to 10,000 EGP']"));
        priceFilter.click();

        // Applying brand filter (example)
        WebElement brandFilter = driver.findElement(By.xpath("//span[text()='Apple']"));
        brandFilter.click();

        // Verify that Apple laptops within the selected price range are displayed
        WebElement result = driver.findElement(By.xpath("//span[contains(text(), 'Apple')]"));
        Assert.assertTrue(result.isDisplayed(), "Apple laptops should be displayed within the selected price range.");
    }

    // Test Case 2: As-you-type search suggestions
    @Test
    public void testAsYouTypeSearchSuggestions() {
        WebElement searchBar = driver.findElement(By.id("twotabsearchtextbox"));
        searchBar.clear();
        searchBar.sendKeys("iph");

        // Verify that suggestions appear
        WebElement suggestion = driver.findElement(By.xpath("//div[contains(@class, 's-suggestion')]"));
        Assert.assertTrue(suggestion.isDisplayed(), "Suggestions should appear as the user types 'iph'.");
    }

    // Test Case 3: Combined filters (price, brand, and rating)
    @Test
    public void testCombinedFilters() {
        performSearch("headphones");

        // Applying price filter
        WebElement priceFilter = driver.findElement(By.xpath("//span[text()='Price: 500 EGP to 1,000 EGP']"));
        priceFilter.click();

        // Applying brand filter
        WebElement brandFilter = driver.findElement(By.xpath("//span[text()='Sony']"));
        brandFilter.click();

        // Applying rating filter
        WebElement ratingFilter = driver.findElement(By.xpath("//span[text()='4 Stars & Up']"));
        ratingFilter.click();

        // Verify that Sony headphones within the selected filters are displayed
        WebElement result = driver.findElement(By.xpath("//span[contains(text(), 'Sony')]"));
        Assert.assertTrue(result.isDisplayed(), "Sony headphones should be displayed within the selected filters.");
    }

    // Test Case 4: Dynamic update of search results when filters are changed
    @Test
    public void testDynamicUpdateOfResults() {
        performSearch("smartphone");

        // Apply initial price filter
        WebElement priceFilter = driver.findElement(By.xpath("//span[text()='Price: 1,000 EGP to 2,000 EGP']"));
        priceFilter.click();

        // Verify initial results
        WebElement resultBeforeChange = driver.findElement(By.xpath("//span[contains(text(), 'Smartphone')]"));
        Assert.assertTrue(resultBeforeChange.isDisplayed(), "Smartphones should be displayed for the selected price range.");

        // Change the price filter
        WebElement newPriceFilter = driver.findElement(By.xpath("//span[text()='Price: 2,000 EGP to 3,000 EGP']"));
        newPriceFilter.click();

        // Verify updated results
        WebElement resultAfterChange = driver.findElement(By.xpath("//span[contains(text(), 'Smartphone')]"));
        Assert.assertTrue(resultAfterChange.isDisplayed(), "Smartphones should update dynamically after changing the price filter.");
    }

    // Test Case 5: Invalid search query
    @Test
    public void testInvalidSearchQuery() {
        performSearch("invalidsearchquery123");

        // Verify "No results found" message
        WebElement noResultsMessage = driver.findElement(By.xpath("//span[contains(text(), 'No results for')]"));
        Assert.assertTrue(noResultsMessage.isDisplayed(), "A 'No results found' message should appear for an invalid search query.");
    }

    // Tear down method to close the browser after the tests
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
