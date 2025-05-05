package Front_java.Configuration;

import javafx.scene.control.TextField;

public class ActiveTextFieldManager {
    private static ActiveTextFieldManager instance;
    private TextField activeTextField;

    private ActiveTextFieldManager() {}

    public static ActiveTextFieldManager getInstance() {
        if (instance == null) {
            instance = new ActiveTextFieldManager();
        }
        return instance;
    }

    public TextField getActiveTextField() {
        return activeTextField;
    }

    public void setActiveTextField(TextField textField) {
        this.activeTextField = textField;
    }

    public void clearActiveTextField() {
        if (activeTextField != null) {
            activeTextField.clear();
        }
    }
}