package hmz.maharmeh;

import atlantafx.base.controls.Popover;
import atlantafx.base.theme.Styles;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ClippedItem extends HBox {
    private Label label = new Label();
    private Button copy;
    private Button delete;
    private String text;
    private static AtomicBoolean coppiedFromApp;
    public ClippedItem(String text, AtomicBoolean copyFromApp, Consumer<ClippedItem>selfDelete){
        this.text = text;
        String labelText = "";
        if(text.length() > 100) {
            labelText = text.substring(0, 100) + "...";
        }else {
            labelText = text;
        }
        label.setText(labelText);
        label.getStyleClass().add(Styles.WARNING);
        label.setWrapText(true);
        setUpButtons(selfDelete);
        Region spacer = new Region();
        setHgrow(spacer, Priority.ALWAYS);
        getChildren().addAll(label,spacer,copy,delete);
        setSpacing(5);
        this.setPadding(new Insets(0,2,0,2));
        coppiedFromApp = copyFromApp;

        //hbox.getStylesheets().add(Styles.BG_WARNING_EMPHASIS);
        //this.getStyleClass().add(Styles.BG_WARNING_EMPHASIS);
        this.getStyleClass().addAll("my-container");
    }

    public String getText() {
        return this.text;
    }

    private void setUpButtons(Consumer<ClippedItem> selfDelete) {

        copy = new Button("",new FontIcon(Feather.COPY));
        delete = new Button("",new FontIcon(Feather.TRASH));




        delete.setOnAction(e -> {
            selfDelete.accept(this);
        });
        delete.getStyleClass().addAll(Styles.FLAT,"trans-button");

        copy.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            Map<DataFormat,Object> map = new HashMap<>();
            map.put(DataFormat.PLAIN_TEXT, this.getText());
            clipboard.setContent(map);
            TextFlow flow = new TextFlow(new Text("Coppied!"));
            Popover popover = new Popover(flow);
            popover.setArrowLocation(Popover.ArrowLocation.BOTTOM_CENTER);
            popover.setDetachable(false);
            popover.show(copy);
            coppiedFromApp.set(true);
        });
        copy.getStyleClass().addAll(Styles.FLAT,"trans-button");
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o == this ) return true;
        if(!(o instanceof ClippedItem)) return false;
        ClippedItem c = (ClippedItem) o;
        return c.getText().equals(this.getText());
    }
}
