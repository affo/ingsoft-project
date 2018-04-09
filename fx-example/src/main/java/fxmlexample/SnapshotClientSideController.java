package fxmlexample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/**
 * This is the client side controller that translates the actions specified in the `snapshot.fxml` file.
 */
public class SnapshotClientSideController {
    @FXML
    private Text label, state;
    @FXML
    private TextField input;

    public void initData(int data) {
        state.setText(">>> " + data + " <<<");
    }

    public void saveState(ActionEvent e) {
        String stringLabel = input.getText();
        if (!stringLabel.isEmpty() && label.getText().isEmpty()) {
            label.setText(stringLabel);
            input.setText("");
            input.setVisible(false);
        }
    }
}
