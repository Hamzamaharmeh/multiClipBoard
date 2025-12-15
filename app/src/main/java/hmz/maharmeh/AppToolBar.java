package hmz.maharmeh;

import atlantafx.base.controls.Popover;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

public class AppToolBar extends ToolBar {
    private Button quit  = new Button("Quit",new FontIcon(Feather.X));
    private Button clear = new Button("Clear",new FontIcon(Feather.TRASH_2));
    private Button tooltip = new Button("",new FontIcon(Feather.HELP_CIRCLE));

    private VBox list = new VBox();
    public AppToolBar(VBox list){

        super();
        this.list = list;
        setUpButtons();
        Region spacer = new Region();
        this.getItems().addAll(clear,spacer,tooltip,quit);
        HBox.setHgrow(spacer, Priority.ALWAYS);
    }
    private void setUpButtons() {

        TextFlow flow = new TextFlow(new Text("""
                Control + space to show
                
                Escape to hide
                
                The app doesn't store duplicates
                The app has a capacity of 25 items
                """));

        Popover popover = new Popover(flow);
        popover.setArrowLocation(Popover.ArrowLocation.TOP_CENTER);
        popover.setDetachable(false);

        tooltip.setOnAction(event -> {
            popover.show(tooltip);
        });
        clear.setOnAction(e -> {
            list.getChildren().clear();
        });
        quit.setOnAction(e ->  {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                throw new RuntimeException(ex);
            }
            Platform.exit();
            System.exit(0);

        });
    }
}
