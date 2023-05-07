package chart.plugin;

import controller.*;
import boundary.widget.*;
import boundary.MainWindow;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.ServiceLoader;

public class BasePlugin extends TabPanel implements BasePluginInterface{
    List<URL> urls;

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
    }

    public void addURL(List<URL> urls){
        this.urls = urls;
    }

    public void pluginLoader(MainWindow mainWindow){
        ClassLoader ucl = new URLClassLoader((URL[]) urls.toArray(new URL[urls.size()]));
        ServiceLoader<ChartPluginInterface> chartPluginInterface = ServiceLoader.load(BasePluginInterface.class, ucl);

        for (final ChartPluginInterface g : chartPluginInterface) {
            JPanel chartPanel = g.loadPanel(mainWindow.getController());
            this.add(chartPanel);
        }
    }
}
