package org.planotius.controller.functions;

import org.planotius.controller.Controller;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.planotius.exception.ElementNotFinded;

/**
 * @author ggodoy
 */
public class Element extends Controller implements WebElement {

    private static final Logger log = Logger.getLogger(Element.class.getName());
    private static final int CLICK_WAIT_TIME = 2000;
    private static final int SENDKEY_WAIT_TIME = 2000;

    String key;
    String keyValue;
    String frame;
    String locator;
    public WebElement webElement;
    Class aclass;
    Field field;
    
    public Element(WebElement element) {
        System.out.println("ELEMENTO > "+ element);
        this.webElement = element;
        System.out.println("ELEMENTO SETADO> "+ this.webElement);
    }

    public Element() {
    }

    
    
    
    /**
     * Get the attribute 'keyValue' of an WebElement.
     * The same of WebElement.getAttribute("keyValue");
     * @return 
     */
    public String getAttributeValue() {
        reload();
        return this.webElement.getAttribute("value");
    }

    /**
     * Returns the value set to the key, on the element,
     * key=value in a property.
     * @return 
     */
    public String getKeyValue() {
        return keyValue;
    }

    /**
     * Set the value to the key, on the element,
     * key=value in a property.
     * @return 
     */
    public void setKeyValue(String value) {
        this.keyValue = value;
    }

    public void setAclass(Class aclass) {
        this.aclass = aclass;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class getAclass() {
        return aclass;
    }

    public Field getField() {
        return field;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getLocator() {
        return locator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
}

    private void reload() {
        if (this.webElement == null) {
            log.debug("webElement null... loadingInputData this.");
            this.webElement = loadInputData(this);
        }
        try {
            log.debug("Calling this.webElement.isDisplayed");
            this.webElement.isDisplayed();
        } catch (StaleElementReferenceException stale) {
            log.debug("Setting this.webElement to null and reloading again...");
            this.webElement = null;
            reload();
        } catch (NullPointerException npe) {
            log.debug("NPE on when trying to call this.webElement.isDisplayed.");
            this.webElement = null;
        }
    }

    
    public void click() {
        String message = "Element ["+ this.key + ":" + this.keyValue + "] not found for click.";
        try {
            reload();
            log.debug("Waiting " + CLICK_WAIT_TIME + " millis before clicking element ["+ this.key + ":" + this.keyValue + "]");
            Thread.sleep(CLICK_WAIT_TIME);
            this.webElement.click();
            log.debug("Element ["+ this.key + ":" + this.keyValue + "] clicked.");
        } catch (Exception e) {
            log.warn(message);
            throw new ElementNotFinded(message);
        }
        waitPageLoad();
    }

    public void clickNoWait() {
        String message = "Element ["+ this.key + ":" + this.keyValue + "] not found for click.";
        try{
            reload();
            log.debug("Waiting " + CLICK_WAIT_TIME + " millis before clicking element ["+ this.key + ":" + this.keyValue + "]");
            Thread.sleep(CLICK_WAIT_TIME);
            this.webElement.click();
            log.debug("Element ["+ this.key + ":" + this.keyValue + "] clicked.");
        } catch (Exception e) {
            log.warn(message);
            throw new ElementNotFinded(message);
        }
    }

    public void submit() {
        reload();
        this.webElement.submit();
    }

    public void sendKeys(CharSequence... css) {
        log.debug("CharSequence size is " + css.length);
        StringBuilder sb = new StringBuilder(css.length);
        for (CharSequence theChar : css) {
            sb.append(theChar);
        }
        try{
            log.debug("Trying to send keys " + sb.toString());
            reload();
            this.webElement.clear();
            log.debug("Waiting " + SENDKEY_WAIT_TIME + " millis before sending " + sb.toString() );
            Thread.sleep(SENDKEY_WAIT_TIME);
            this.webElement.sendKeys(css);
        } catch (Exception e) {
            log.warn("Error trying to send keys " + sb.toString());
            throw new ElementNotFinded("Unable to send keys " + sb.toString());
        } finally {
            sb.setLength(0);
            sb=null;
        }
    }

    public void selectOnlist(Object value) {
        reload();
        waitCurrentPageLoad();
        List<WebElement> options = this.webElement.findElements(By.tagName("option"));
        
        
        if (value instanceof String){
        
        if (!value.equals("")) {
            for (WebElement option : options) {
                if (option.getText().equals(value)) {
                    option.click();
                    return;
                }
            }
        }
        }
        else if (value instanceof Integer){
            int index = (Integer) value;
            options.get(index).click();
        }
    }
    

    public void clear() {
        reload();
        this.webElement.clear();
    }

    public String getTagName() {
        reload();
        return this.webElement.getTagName();
    }

    public String getAttribute(String string) {
        reload();
        return this.webElement.getAttribute(string);
    }

    public boolean isSelected() {
        reload();
        return this.webElement.isSelected();
    }

    public boolean isEnabled() {
        reload();
        return this.webElement.isEnabled();
    }

    public String getText() {
        reload();
        return this.webElement.getText();
    }

    public List<WebElement> findElements(By by) {
        reload();
        return this.webElement.findElements(by);
    }

    public WebElement findElement(By by) {
        reload();
        return this.webElement.findElement(by);
    }

    public boolean isDisplayed() {
        reload();

        if (this.webElement == null) {
            return false;
        } else {
            return this.webElement.isDisplayed();
        }
    }

    public Point getLocation() {
        reload();
        return this.webElement.getLocation();
    }

    public Dimension getSize() {
        reload();
        return this.webElement.getSize();
    }

    public String getCssValue(String string) {
        reload();
        return this.webElement.getCssValue(string);
    }

    private void waitCurrentPageLoad() {
        try {
            By element = By.xpath("//*[not (.='')]");
            WebDriverWait wait = new WebDriverWait(getDriver(), 120);
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (Exception e) {
        }
    }

    public boolean waitElementDisplayed() {
        try {
            while (this.webElement == null) {
                reload();
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Cause a webDriver wait until all elements is visible.
     *
     * @return
     */
    public void waitPageLoad() {
        try {
            Calendar init = Calendar.getInstance();
            waitCurrentPageLoad();
            Calendar finish = Calendar.getInstance();
            log.debug(getDriver().getCurrentUrl() + " levou  " + (finish.getTimeInMillis() - init.getTimeInMillis()) + " ms. para carregar.");
        } catch (Exception e) {
            Controller controller = new Controller() {
            };
            File file = new File("target/CapturedErrors/");
            if (!file.exists()) {
                file.mkdir();
            }
            controller.printScreen("target/CapturedErrors/waitPageLoad" + e.getMessage() + "-" + Calendar.getInstance() + ".png");
        }
    }

    /**
     * Return a cell keyValue based on provided parameters for column and row. For
     * row and columns, inform String or Integer types.
     *
     * @param lookupRow
     * @param lookupColumn
     * @return
     */
    public String getCellValueFromTable(Object lookupRow, Object lookupColumn) {
        try {
            reload();
            List<WebElement> lines = this.findElements(By.tagName("tr"));

            int rowNum, colNum, headerNum;
            rowNum = 0;

            ArrayList<String> columns = new ArrayList<String>();
            ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

            int lookupColumIndex = 0;
            int lookupRowIndex = 0;
            boolean hasHeader = false;

            for (WebElement line : lines) {
                colNum = 0;
                headerNum = 0;

                List<WebElement> cols = line.findElements(By.xpath("td"));

                List<WebElement> headers = line.findElements(By.tagName("th"));
                for (WebElement header : headers) {
                    if (lookupColumn instanceof String) {
                        if (header.getText().equals(lookupColumn)) {
                            lookupColumIndex = headerNum;
                            hasHeader = true;
                        }
                    } else if (lookupColumn instanceof Integer) {
                        lookupColumIndex = (Integer) lookupColumn;
                    } else {
                        return null;
                    }

                    headerNum++;
                }
                for (WebElement col : cols) {
                    columns.add(col.getText());

                    if (!hasHeader) {
                        if (lookupColumn instanceof String) {
                            if (col.getText().equals(lookupColumn)) {
                                lookupColumIndex = colNum;
                            }
                        } else if (lookupColumn instanceof Integer) {
                            lookupColumIndex = (Integer) lookupColumn;
                        } else {
                            return null;
                        }
                    }

                    if (lookupRow instanceof String) {
                        if (col.getText().equals(lookupRow)) {
                            lookupRowIndex = rowNum;
                        }
                    } else if (lookupRow instanceof Integer) {
                        lookupRowIndex = (Integer) lookupRow;
                    } else {
                        return null;
                    }

                    colNum++;
                }
                rows.add(columns);
                columns = new ArrayList<String>();
                rowNum++;
            }

            return rows.get(lookupRowIndex).get(lookupColumIndex);

        } catch (StaleElementReferenceException e) {
            return getCellValueFromTable(lookupRow, lookupColumn);
        } catch (Exception e) {
            return null;
        }

    }

    
    
    
    //    /*Percorre o header de uma tabela e encontra a coluna desejada,
//     após localizada o método retornará o número da coluna*/
//    @Deprecated
//    private Integer numeroDaColuna(String nomeColuna) {
//        String nomeTabela = this.getValue();
//        int numeroDaColuna = 0;
//        List<WebElement> columns = this.findElements(By.cssSelector("td[id^='td_solicitacaoPlanejamentoAluno']"));
//        int qtdeColunas = columns.size();
//        for (int i = 1; i <= qtdeColunas; i++) {
//            String sValue = null;
//            sValue = getDriver().findElement(By.xpath(".//*[@id='" + nomeTabela + "']/thead/tr[1]/th[" + i + "]")).getText();
//            if (sValue.equalsIgnoreCase(nomeColuna)) {
//                numeroDaColuna = i;
//                break;
//            }
//        }
//        return numeroDaColuna;
//    }
//    
//    @Deprecated
//    private void selecionarCheckboxTabelaResultado(String valorReferencia, String colunaReferencia, Element botaoNext) {
//        List<WebElement> linhas = this.findElements(By.cssSelector("tr[class^='tableRow']"));
//        int qtdeLinhas = linhas.size();
//        int coluna1 = this.numeroDaColuna(colunaReferencia);
//
//        for (int j = 1; j <= qtdeLinhas; j++) {
//
//            rowItemValue = getDriver().findElement(By.xpath("./*//*[@id='" + this.getValue() + "']/tbody/tr[" + j + "]/td[" + coluna1 + "]")).getText();
//
//            if (qtdeLinhas == 0) {
//                rowItemValue = null;
//                System.out.println("Consulta sem resultado! Linha:" + rowItemValue);
//            }
//            if (qtdeLinhas == 1) {
//                if (rowItemValue.equalsIgnoreCase(valorReferencia)) {
//                    linhaEsperada = j;
//                    break;
//                } else {
//                    System.out.println("Valor de referencia não encontrado na lista:" + rowItemValue);
//                }
//            }
//        }
//
//        if (botaoNext.isEnabled() && qtdeLinhas > 1) {
//            while (!valorReferencia.equals(rowItemValue)) {
//                for (int j = 1; j <= qtdeLinhas; j++) {
//                    rowItemValue = getDriver().findElement(By.xpath("./*//*[@id='" + this.getValue() + "']/tbody/tr[" + j + "]/td[" + coluna1 + "]")).getText();
//                    linhaEsperada = j;
//                    if (j == qtdeLinhas) {
//                        botaoNext.click();
//                        j = 1;
//                    } else if (valorReferencia.equalsIgnoreCase(rowItemValue)) {
//                        break;
//                    }
//                }
//            }
//            getDriver().findElement(By.xpath("./*//*[@id='" + this.getValue() + "']/tbody/tr[" + linhaEsperada + "]/td[1]/input")).click();
//        }
//    }
}
