package com.guigarage.responsive;
2  
3  import com.guigarage.ui.WindowUtilities;
4  import  javafx.beans.InvalidationListener;
5  import  javafx.beans.property.ObjectProperty;
6  import  javafx.beans.property.ObjectPropertyBase;
7  import  javafx.beans.property.SimpleStringProperty;
8  import  javafx.beans.property.StringProperty;
9  import  javafx.beans.value.ChangeListener;
10 import  javafx.scene.Node;
11 import  javafx.stage.Window;
12 
13 import  javafx.beans.property.BooleanProperty;
14 
15 
16 public class ResponsiveHandler {
17 
18     private static String PROP_MANAGED_STATE = "responsivefx-preset-managed-state";
19     
20     private static InvalidationListener MANAGED_LISTENER = e -> {
21        BooleanProperty managed = (BooleanProperty) e;
22        Node node = (Node) managed.getBean();
23        node.getProperties().put(PROP_MANAGED_STATE, node.isManaged());
24    };
25 
26     public static void More ...addResponsiveToWindow(Window window) {
27         StringProperty stylesheet = new SimpleStringProperty(getCurrentResponsiveStylesheet(window));
28         WindowUtilities.bindStyleSheetToWindow(window, stylesheet);
29 
30         updatePseudoClassesForAllChildren(window);
31         //TODO: Falsch! Hier muss der ursprünglich gesetzte Wert gespeichert werden! managed müsste eine styledProperty sein
32         updateManagedPropertyForAllChildren(window);
33 
34         WindowUtilities.registerRecursiveChildObserver(window, n -> removeAllPseudoClasses(n), n -> updatePseudoClasses(n, getTypeForWindow(window)));
35 
36         window.widthProperty().addListener(e -> {
37             stylesheet.setValue(getCurrentResponsiveStylesheet(window));
38             updatePseudoClassesForAllChildren(window);
39             updateManagedPropertyForAllChildren(window);
40         });
41         window.getScene().getRoot().layout();
42     }
43 
44     private static DeviceType More ...getTypeForWindow(Window window) {
45         return DeviceType.getForWidth(window.getWidth());
46     }
47 
48     private static String More ...getCurrentResponsiveStylesheet(Window window) {
49         return getTypeForWindow(window).getStylesheet();
50     }
51 
52 
53     private static void More ...updateManagedPropertyForAllChildren(Window window) {
54         WindowUtilities.getAllNodesInWindow(window).forEach(n -> {
55             updateManagedProperty(n, getTypeForWindow(window));
56         });
57     }
58 
59     private static void More ...updateManagedProperty(Node n, DeviceType type) {
60         // first time we've set this invisible => store the preset
61       if ( !n.getProperties().containsKey(PROP_MANAGED_STATE)) {
62           n.getProperties().put(PROP_MANAGED_STATE, n.isManaged()) ;         
63       }
64       // don't track changes through this
65        n.managedProperty().removeListener(MANAGED_LISTENER);
66        // If it's visible we use the stored value for "managed" property
67        n.setManaged(n.isVisible()? (Boolean)n.getProperties().get(PROP_MANAGED_STATE):false);
68        // need to track changes through API
69        n.managedProperty().addListener(MANAGED_LISTENER);
70     }
71 
72     private static void More ...updatePseudoClassesForAllChildren(Window window) {
73         WindowUtilities.getAllNodesInWindow(window).forEach(n -> {
74             updatePseudoClasses(n, getTypeForWindow(window));
75         });
76     }
77 
78     private static void More ...updatePseudoClasses(Node n, DeviceType type) {
79         type.getInactiveClasses().forEach(pseudoClass -> n.pseudoClassStateChanged(pseudoClass, false));
80         type.getActiveClasses().forEach(pseudoClass -> n.pseudoClassStateChanged(pseudoClass, true));
81         deviceTypeProperty.set(type);
82     }
83 
84     private static void More ...removeAllPseudoClasses(Node n) {
85         DeviceType.getAllClasses().forEach(pseudoClass -> n.pseudoClassStateChanged(pseudoClass, false));
86     }
87 
88 
89     // ******************** Event handling *************************************
90     public static final ObjectProperty<DeviceType> deviceTypeProperty = new ObjectPropertyBase<DeviceType>(DeviceType.NONE) {
91         @Override public Object More ...getBean() { return this; }
92         @Override public String More ...getName() { return "deviceType"; }
93     };
94     public static final void More ...setOnDeviceTypeChanged(final InvalidationListener LISTENER) {
95         deviceTypeProperty.addListener(LISTENER);
96     }
97     public static final void More ...setOnDeviceTypeChanged(final ChangeListener<DeviceType> LISTENER) {
98         deviceTypeProperty.addListener(LISTENER);
99     }
100}