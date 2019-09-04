package com.app

import com.vaadin.annotations.Theme
import com.vaadin.ui.Button
import com.vaadin.ui.Notification
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Label
import com.vaadin.grails.Grails
import groovy.transform.CompileStatic

@CompileStatic
@Theme('mytheme')

class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        print("HElllllloooooooooooooooooooooooooo")
		VerticalLayout layout = new VerticalLayout()
        layout.setMargin(true)

        String homeLabel = Grails.i18n("default.home.label")
        Label label = new Label(homeLabel)
        layout.addComponent(label)
        String str ="Hello World!"
        Label label1 =new Label(str)
        layout.addComponent(label1)
        // example of how to get Grails service and call a method
        // List<User> users = Grails.get(UserService).getListOfUsers()
        //    for (User user : users) {
        //    	layout.addComponent(new Label(user.name))
        // }
     //   HorizontalLayout root = new HorizontalLayout()
       // root.setSizeFull()

        Button button = new Button("Click me!", {
            Notification.show("Hello from Vaadin 8 + Grails 3!", Notification.Type.ERROR_MESSAGE)
        } as Button.ClickListener)
        layout.addComponent(button)

		setContent(layout)
    }
}
