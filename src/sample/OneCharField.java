package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;

public class OneCharField extends TextField {

    private final IntegerProperty maxLength;

    public OneCharField() {
        super();
        this.maxLength = new SimpleIntegerProperty(1);
    }

    public IntegerProperty maxLengthProperty() {
        return this.maxLength;
    }

    public final Integer getMaxLength() {
        return this.maxLength.getValue();
    }

    @Override
    public void replaceText(int start, int end, String insertedText) {
        if (this.getMaxLength() <= 0) {
            super.replaceText(start, end, insertedText);
        }
        else {
            String currentText = this.getText() == null ? "" : this.getText();

            String finalText = currentText.substring(0, start) + insertedText + currentText.substring(end);

            int numberOfexceedingCharacters = finalText.length() - this.getMaxLength();
            if (numberOfexceedingCharacters <= 0) {
                super.replaceText(start, end, insertedText);
            }
            else {
                String cutInsertedText = insertedText.substring(
                        0,
                        insertedText.length() - numberOfexceedingCharacters
                );

                super.replaceText(start, end, cutInsertedText);
            }
        }
    }
}