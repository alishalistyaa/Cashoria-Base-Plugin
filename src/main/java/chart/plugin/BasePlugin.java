package chart.plugin;

import controller.*;
import boundary.widget.*;
import boundary.MainWindow;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasePlugin extends TabPanel implements BasePluginInterface{

    public void addChartPanels(JPanel chart){
       this.add(chart);
    }
    @Override
    public void setupMainWindow(MainWindow mainWindow){
        System.out.println("Test SetupMainwindow Akhir");
        SideBar sidePanel = mainWindow.getSidePanel();
        TopBar topBar = mainWindow.getTopBar();
        topBar.addArrayType("Chart");

        sidePanel.addButton(new SideBarButton("/pengaturan.png", "Chart"), "chartButton")
                .addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!topBar.hasType("Chart"))
                            mainWindow.addWindow("Chart", new BasePlugin(), "Chart");
                        else {
                            TopBarTab tab = ((TopBarTab) topBar
                                    .getComponent(topBar.getTabsWithType("Chart").get(0)));
                            if (!tab.getStatus())
                                tab.doClick();
                        }
                    }
                }
                        );
        System.out.println("Test SetupMainwindow Akhir");
    }
    @Override
    public JPanel loadPanel(MainController mainController){
        System.out.println("Test Load Panel");
        return this;
    }
}
