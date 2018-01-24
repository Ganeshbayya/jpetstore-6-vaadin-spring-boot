package com.kiroule.jpetstore.vaadinspring.ui.form;

import com.google.common.collect.Lists;

import com.kiroule.jpetstore.vaadinspring.domain.ShippingDetails;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.v7.data.Validator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.v7.ui.Field;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.v7.ui.TextField;

import org.vaadin.viritinv7.fields.MTextField;
import org.vaadin.viritinv7.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringComponent
@ViewScope
public class ShippingDetailsForm extends AbstractForm<ShippingDetails> {

  private static final long serialVersionUID = 3450336789838413879L;

  private TextField firstName = new MTextField("First Name").withNullRepresentation("");
  private TextField lastName = new MTextField("LastName").withNullRepresentation("");
  private TextField email = new MTextField("Email").withNullRepresentation("");
  private TextField phone = new MTextField("Phone").withNullRepresentation("");
  private TextField address1 = new MTextField("Address 1").withNullRepresentation("");
  private TextField address2 = new MTextField("Address 2").withNullRepresentation("");
  private TextField city = new MTextField("City").withNullRepresentation("");
  private TextField state = new MTextField("State").withNullRepresentation("");
  private TextField zip = new MTextField("ZIP Code").withNullRepresentation("");
  private TextField country = new MTextField("Country").withNullRepresentation("");

  @PostConstruct
  public void init() {
    setStyleName(JPetStoreTheme.BASE_FORM);
    setEagerValidation(false);
    setHeightUndefined();
  }

  public boolean validate() {

    try {
      getFieldGroup().getFields().forEach(field -> {
        field.focus();
        field.validate();
      });
    } catch (Validator.InvalidValueException e) {
      Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  public void clear() {
    setEntity(new ShippingDetails());
  }

  @Override
  protected Component createContent() {

    email.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address1.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);
    address2.setStyleName(JPetStoreTheme.WIDE_TEXT_FIELD);

    setToolBarVisible();
    setRequiredFields(firstName, lastName, email, phone, address1, city, state, zip, country);

    MFormLayout shippingDetailsFormLayout = new MFormLayout(firstName, lastName, email, phone, address1, address2,
        city, state, zip, country).withWidth("-1px");

    return new MVerticalLayout(
        new Panel("Shipping Address", shippingDetailsFormLayout)
    );
  }

  private void setRequiredFields(Field<?>... fields) {

    Lists.newArrayList(fields).forEach(field -> {
      field.setRequired(true);
      field.setRequiredError(field.getCaption() + " is required");
    });
  }

  private void setToolBarVisible() {

    getSaveButton().setVisible(true);
    getSaveButton().setCaption("Continue");
    getResetButton().setVisible(true);
    getResetButton().setCaption("Clear");
  }
}