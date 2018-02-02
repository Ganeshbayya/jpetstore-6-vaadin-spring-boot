package com.kiroule.jpetstore.vaadinspring.ui.component;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.ui.converter.CurrencyConverter;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIAddItemToCartEvent;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.form.ProductItemForm;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.HasUIEventBus;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritinv7.fields.MTable;

import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class ItemListTable extends MTable<Item> implements HasUIEventBus {

  private static final long serialVersionUID = -2847546238729364925L;

  @Autowired
  private ProductItemForm productItemForm;
  @Autowired
  private UIEventBus uiEventBus;

  public ItemListTable() {

    addContainerProperty("addToCart", Component.class, null);
    addContainerProperty("description", String.class, "Not defined");

    withProperties("itemId", "description", "listPrice", "addToCart");
    withColumnHeaders("Item ID", "Description", "List Price", "");
    setSortableProperties("itemId", "listPrice");
    withGeneratedColumn("itemId", item -> {
          Button itemIdButton = new Button(item.getItemId(), this::viewDetails);
          itemIdButton.addStyleName(JPetStoreTheme.BUTTON_LINK);
          itemIdButton.setData(item);
          return itemIdButton;
    });
    withGeneratedColumn("description", item -> item.getAttribute1() + " " + item.getProduct().getName());
    withGeneratedColumn("addToCart",
            item -> new Button("Add to Cart", event -> getUIEventBus().post(new UIAddItemToCartEvent(item))));
    setConverter("listPrice", new CurrencyConverter());
    withFullWidth();
  }

  private void viewDetails(Button.ClickEvent event) {
    Item item = (Item) event.getButton().getData();
    productItemForm.setEntity(item);
    productItemForm.openInModalPopup().setCaption(getPopupCaption(item));
  }

  private String getPopupCaption(Item item) {
    return format("%s | %s", item.getProductId(), item.getProduct().getName());
  }
}