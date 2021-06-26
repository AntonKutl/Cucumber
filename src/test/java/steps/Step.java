package steps;

import enums.Category;
import enums.Sort;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Step {

    private WebDriver driver;
    private JavascriptExecutor js;

    @ParameterType(".*")
    public Sort sort(String sort) {
        return Sort.valueOf(sort);
    }

    @ParameterType(".*")
    public Category category(String category) {
        return Category.valueOf(category);
    }


    @Когда("Открыт ресурс Авито")
    public void открытРесурсАвито() {
        System.setProperty("webdriver.chrome.driver", "D:\\WebDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
        driver.get("https://www.avito.ru");
        driver.manage().window().maximize();
    }

    @И("В выпадающем списке категорий выбрана {category}")
    public void вВыпадающемСпискеКатегорийВыбранаCategory(Category category) {

        WebElement elementСategory = driver.findElement(By.id("category"));
        Select select = new Select(elementСategory);
        select.selectByVisibleText(category.getValue());

    }

    @И("В поле поиска введено значение {string}")
    public void вПолеПоискаВведеноЗначениеStringForSearch(String stringForSearch) throws InterruptedException {
        Thread.sleep(1000);
        WebElement searchSearch = driver.findElement(By.id("search"));
        searchSearch.sendKeys(stringForSearch);
        searchSearch.sendKeys(Keys.RETURN);
    }

    @Тогда("Кликнуть по выпадающему списку региона")
    public void кликнутьПоВыпадающемуСпискуРегиона() {
        driver.findElement(By.className("main-text-2PaZG")).click();
    }

    @Тогда("В поле регион ввести {string}")
    public void вПолеРегионВвестиCity(String city) throws InterruptedException {
        driver.findElement(By.className("suggest-input-3p8yi")).sendKeys(city);
        Thread.sleep(1000);
        driver.findElement(By.className("popup-buttons-NqjQ3")).click();
    }

    @И("Нажата кнопка показать объявление")
    public void нажатаКнопкаПоказатьОбъявление() {
        WebElement checkDelivery = driver.findElement(By.xpath("//div[@data-marker=\"delivery-filter/container\"]"));
        js.executeScript("arguments[0].scrollIntoView();", checkDelivery);
        driver.findElement(By.xpath("//button[contains(@data-marker,'search-filters')]")).click();
    }

    @Тогда("Открылась страница результаты по запросу {string}")
    public void открыласьСтраницаРезультатыПоЗапросуStringForSearch(String stringForSearch) {

        String text = driver.findElement(By.cssSelector(".page-title-text-WxwN3")).getText();
        Assert.assertTrue(text.contains(stringForSearch));

    }

    @И("Активирован чекбокс только с фотографией")
    public void активированЧекбоксТолькоСФотографией() throws InterruptedException {
        Thread.sleep(1000);
        if (!driver.findElement(By.xpath("//span[contains(text(), 'только с фото')]")).isSelected()) {
            driver.findElement(By.xpath("//span[contains(text(), 'только с фото')]")).click();
        }
    }

    @И("В выпадающем списке сортировка выбрано значение {sort}")
    public void вВыпадающемСпискеСортировкаВыбраноЗначениеSort(Sort sort) {
        WebElement elementSelect = driver.findElement(By.xpath("//select[contains(@class,'select-select-3CHiM')]"));
        elementSelect.findElement(By.xpath("//option[. = '" + sort.getValue() + "']")).click();
    }

    @И("В консоль выведено значение название цены {int} первых товаров")
    public void вКонсольВыведеноЗначениеНазваниеЦеныQuantityПервыхТоваров(int quantity) {
        List<WebElement> name = driver.findElements(By.xpath("//h3[contains(@itemprop,'name')]"));
        List<WebElement> cost = driver.findElements(By.xpath("//span[contains(@class,'price-text-1HrJ_ text-text-1PdBw text-size-s-1PUdo')]"));
        for (int i = 0; i < quantity; i++) {
            System.out.println(name.get(i).getText() + " цена " + cost.get(i).getText());
        }
    }

}
