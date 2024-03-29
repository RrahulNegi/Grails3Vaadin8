package hello.world

import com.vaadin.data.ValueProvider
import com.vaadin.server.FontAwesome
import com.vaadin.server.VaadinRequest
import com.vaadin.shared.ui.ValueChangeMode
import com.vaadin.ui.Button
import com.vaadin.ui.CssLayout
import com.vaadin.ui.Grid
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.TextField
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import org.springframework.beans.factory.annotation.Autowired

//@CompileStatic
//@Theme('mytheme')
class MyUI extends UI {

    //@Autowired
    private CustomerService contactService

    private TextField filter = new TextField()
    private Grid grid = new Grid()
    private Button clearFilterTextBtn = new Button(FontAwesome.TIMES)
    private CustomerForm form = new CustomerForm(this)


    @Override
    protected void init(VaadinRequest request) {

        HorizontalLayout main = new HorizontalLayout(grid, form)
        main.setSizeFull()
        grid.setSizeFull()
        main.setExpandRatio(grid, 1)

        form.visible = false

        final VerticalLayout layout = new VerticalLayout()

        grid.addColumn(new ContactValueProvider("firstName")).caption = "First Name"
        grid.addColumn(new ContactValueProvider("lastName")).caption = "Last Name"
        grid.addColumn(new ContactValueProvider("email")).caption = "E-mail"

        grid.asSingleSelect().addValueChangeListener( { e ->
            if (!e.value) {
                form.setVisible(false)
            } else {
                form.setCustomer(e.value)
            }
        })

        Button addCustomerBtn = new Button("Add new customer")
        addCustomerBtn.addClickListener( { e ->
            grid.asSingleSelect().clear()
            form.customer = new Customer()
        })



        filter.placeholder = "filter by name…"
        filter.valueChangeMode = ValueChangeMode.LAZY
        filter.addValueChangeListener({e-> updateList()})

        clearFilterTextBtn.description = "Clear the current filter"
        clearFilterTextBtn.addClickListener( { e-> filter.clear()})

        CssLayout filtering = new CssLayout()
        filtering.addComponents(filter, clearFilterTextBtn)
        filtering.styleName = ValoTheme.LAYOUT_COMPONENT_GROUP


        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn)

        layout.addComponents(toolbar, main)

        // fetch list of Customers from service and assign it to Grid
        updateList()

        content = layout

    }

    private void updateList() {
     //   List<Customer> contacts = contactService.findAllByNames(filter.value)
        println("In MYUI "+contactService)
        List<Customer> contacts = contactService.findAll()
        grid.items = contacts.toList()
       // grid.setItems(contactService.findAll())
    }

    private class ContactValueProvider implements ValueProvider<Customer, String> {

        private final String propertyName

        ContactValueProvider(String propertyName) {
            this.propertyName = propertyName
        }

        @Override
        String apply(Customer contact) {
            return contact[propertyName]
        }
    }

}


/* @Override
 protected void init(VaadinRequest vaadinRequest) {
     print("HElllllloooooooooooooooooooooooooo")
     VerticalLayout layout = new VerticalLayout()
     layout.setMargin(true)

     String homeLabel = Grails.i18n("default.home.label")
     Label label = new Label(homeLabel)
     layout.addComponent(label)

     // example of how to get Grails service and call a method
     // List<User> users = Grails.get(UserService).getListOfUsers()
     //    for (User user : users) {
     //    	layout.addComponent(new Label(user.name))
     // }

     setContent(layout)
 }
}
*/