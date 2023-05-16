package Admin_y_Usuario;

import Clases_auxiliares.ListaEnlazada;
import Clases_auxiliares.Message;
import Clases_auxiliares.Nodo;
import Clases_auxiliares.Usuario;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MasterApp extends Application implements Runnable{

    //Cosas relacionadas al conectarse al servidor
    Socket socket;

    //Puente de salida de mensajes entre el cliente y server
    ObjectOutputStream out;
    //Puente de entrada de mensajes entre el cliente y server
    ObjectInputStream in;

    //Hilo
    Thread hilo;

    public MasterApp() {

        try {

            //Inicializa el socket en un host y puerto específico
            this.socket = new Socket("localhost", 1234);


            //Inicializa el envio y entrega de información
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            //Crea un hilo
            this.hilo = new Thread(this);
            hilo.start();

            System.out.println("socket masterapp: " + socket);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    //Labels
    Label labelMain, labelCliente;

    //Botones
    Button botonIniciarSesion;

    //Caja de texto
    TextField usuario;
    PasswordField password;

    //Stage
    Stage stage;


    @Override
    public void start(Stage primaryStage){

        stage = primaryStage;
        StackPane mainPane = new StackPane();  //StackPane principal
        StackPane loginPane = new StackPane();  //StackPane del login

        //mainPane--------------------------------------------
        //Labels
        labelMain = new Label();
        labelMain.setText("Administradores");
        labelMain.setTranslateX(0);
        labelMain.setTranslateY(-250);
        labelMain.setStyle("-fx-font-size: 20;");

        //loginPane--------------------------------------------
        //Label
        labelCliente = new Label();
        labelCliente.setText("Login");
        labelCliente.setTranslateX(0);
        labelCliente.setTranslateY(-100);
        labelCliente.setStyle("-fx-font-size: 15;");

        //Botones
        botonIniciarSesion = new Button();
        botonIniciarSesion.setText("Iniciar sesión");
        botonIniciarSesion.setStyle("-fx-font-size: 12;");
        botonIniciarSesion.setOnAction(e -> comprobarLogin(usuario.getText(), password.getText()));
        botonIniciarSesion.setTranslateY(100);

        //Cajas de texto
        usuario = new TextField();
        usuario.setPromptText("Usuario");
        usuario.setMaxWidth(120);
        usuario.setTranslateY(-30);

        password = new PasswordField();
        password.setPromptText("Contraseña");
        password.setMaxWidth(120);
        password.setTranslateY(10);

        //Caracteristicas del StackPane del cliente
        loginPane.setStyle("-fx-border-color: #000");
        loginPane.setMaxWidth(220);
        loginPane.setMaxHeight(300);

        //Agregar elementos graficos al loginPane
        loginPane.getChildren().addAll(
                labelCliente,
                botonIniciarSesion,
                usuario,
                password
        );

        //Agregar elementos graficos al mainPane
        mainPane.getChildren().addAll(
                labelMain,
                loginPane
        );


        Scene menu = new Scene(mainPane, 500, 700);
        primaryStage.setTitle("MasterApp");
        primaryStage.setScene(menu);

        primaryStage.show();






    }




    //Definir objetos de la interfaz

    //Lista de personas de ejemplo
    ObservableList<Usuario> personas = FXCollections.observableArrayList();

    ListView<Usuario> listaPersonas = new ListView<>(personas);

    //Labels
    Label labelAgregarPlatillo,
            labelUserSelect;

    //Boton de opciones
    ComboBox<String> menuButton;

    //MainPane
    Pane paneMain,paneSuperior,paneOpcion1,paneOpcion2;

    //Cajas de texto
    TextField userField_Pane2,
            passWordField_Pane2,
            textField_platillo,
            textField_calorias,
            textField_precio,
            textField_tiempo;

    //TabPane y Tab
    TabPane tabPane;
    Tab tab1,tab2;

    //Botones
    Button botonAgregarAdministrador,
            botonEliminarAdministrador,
            botonEditarAdministrador,
            botonnAgregarPlato;

    //Stage

    //Indice de un elemento seleccionado de la lista
    int index;

    /**
     * Se encarga de cargar los elementos gráficos de la interfaz
     */
    public void elementosGraficos() throws IOException {
        paneMain = new Pane();

        //Panel de la zona superior, separa la barra de opciones de los otros Pane
        paneSuperior = new Pane();
        //paneSuperior.setStyle("-fx-border-color: #ff0000");
        paneSuperior.setMaxHeight(26);

        //Pane correspondiente a la opción #1 del ComboBox
        paneOpcion1 = new Pane();
        //paneOpcion1.setStyle("-fx-border-color: #0048ff");
        paneOpcion1.setMinWidth(1200);  //Anchura mínima
        paneOpcion1.setMinHeight(700);  //Altura mínima
        paneOpcion1.setTranslateY(26);

        //Pane correspondiente a la opción #2 del ComboBox
        paneOpcion2 = new Pane();
        //paneOpcion2.setStyle("-fx-border-color: #00ff23");
        paneOpcion2.setMinWidth(1200);  //Anchura mínima
        paneOpcion2.setMinHeight(700);  //Altura mínima
        paneOpcion2.setTranslateY(26);
        paneOpcion2.setVisible(false);

        //Es donde todos los elementos se alojan
        tabPane = new TabPane();

        /*
        Se define los Tab, las pestañas del TabPane
         */
        //Tab 1
        tab1 = new Tab();  //Pestaña del TabPane
        tab1.setText("Menus");
        tab1.setClosable(false);  //Desactivar el cierre

        //Tab 2
        tab2 = new Tab();  //Pestaña del TabPane
        tab2.setText("Opciones de administrador");
        tab2.setClosable(false);  //Desactivar el cierre

        //Menu de opciones
        menuButton = new ComboBox<>();
        menuButton.setPromptText("Herramientas");
        menuButton.getItems().addAll(
                "Administrar platillos",
                "Administrar administradores"
        );


        //Elementos correspondientes al Tab 1

        //Elementos correspondientes al Tab 2

        //Detecta cuando un elemento de la lista es seleccionado
        menuButton.setOnAction(new EventHandler<ActionEvent>() {  //Detecta un evento al seleccionar un Item del ComboBox
            @Override
            public void handle(ActionEvent event) {
                int selectedItem = menuButton.getSelectionModel().getSelectedIndex();  //Se obtiene el índice del evento

                if (selectedItem == 0){  //Si el item seleccionado es el primero, mostrar el Pane 1
                    paneOpcion1.setVisible(true);
                    paneOpcion2.setVisible(false);
                }else{  //Si el item seleccionado es el segundo, mostrar el Pane 2
                    paneOpcion1.setVisible(false);
                    paneOpcion2.setVisible(true);
                }

            }
        });




        //Elementos gráficos del paneOpción1

        //Creación de un nuevo platillo

        //Creación de un nuevo administrador
        labelAgregarPlatillo = new Label();
        labelAgregarPlatillo.setText("Agregar un nuevo platillo");
        labelAgregarPlatillo.setTranslateX(570);
        labelAgregarPlatillo.setTranslateY(175);

        textField_platillo = new TextField();
        textField_platillo.setPromptText("Nombre");
        textField_platillo.setStyle("-fx-alignment: center");
        textField_platillo.setTranslateX(570);
        textField_platillo.setTranslateY(250);

        textField_calorias = new TextField();
        textField_calorias.setPromptText("Calorias");
        textField_calorias.setStyle("-fx-alignment: center");
        textField_calorias.setTranslateX(570);
        textField_calorias.setTranslateY(300);

        textField_precio = new TextField();
        textField_precio.setPromptText("Precio");
        textField_precio.setStyle("-fx-alignment: center");
        textField_precio.setTranslateX(570);
        textField_precio.setTranslateY(350);

        textField_tiempo = new TextField();
        textField_tiempo.setPromptText("tiempo(segundos)");
        textField_tiempo.setStyle("-fx-alignment: center");
        textField_tiempo.setTranslateX(570);
        textField_tiempo.setTranslateY(400);

        botonnAgregarPlato = new Button();
        botonnAgregarPlato.setText("Agregar");
        botonnAgregarPlato.setTranslateX(580);
        botonnAgregarPlato.setTranslateY(450);





        //Elementos correspondientes al paneOpcion2

        userField_Pane2 = new TextField();
        userField_Pane2.setPromptText("Nuevo usuario");
        userField_Pane2.setStyle("-fx-alignment: center");
        userField_Pane2.setTranslateX(570);
        userField_Pane2.setTranslateY(250);

        passWordField_Pane2 = new TextField();
        passWordField_Pane2.setPromptText("Contraseña");
        passWordField_Pane2.setStyle("-fx-alignment: center");
        passWordField_Pane2.setTranslateX(570);
        passWordField_Pane2.setTranslateY(300);

        botonEliminarAdministrador = new Button();
        botonEliminarAdministrador.setText("Eliminar");
        botonEliminarAdministrador.setOnAction(e -> eliminarAdministrador(labelUserSelect.getText()));
        botonEliminarAdministrador.setTranslateX(400);
        botonEliminarAdministrador.setTranslateY(260);

        botonEditarAdministrador = new Button();
        botonEditarAdministrador.setText("Editar");
        botonEditarAdministrador.setOnAction(e -> editarAdministrador(
                labelUserSelect.getText(),
                userField_Pane2.getText(),
                passWordField_Pane2.getText()
        ));
        botonEditarAdministrador.setTranslateX(570);
        botonEditarAdministrador.setTranslateY(360);

        botonAgregarAdministrador = new Button();
        botonAgregarAdministrador.setText("Agregar");
        botonAgregarAdministrador.setOnAction(e -> agregarNuevoAdmin(userField_Pane2.getText(),passWordField_Pane2.getText()));
        botonAgregarAdministrador.setTranslateX(620);
        botonAgregarAdministrador.setTranslateY(360);


        labelUserSelect = new Label();
        labelUserSelect.setText("GG IZZI PIZZI");
        labelUserSelect.setTranslateX(400);
        labelUserSelect.setTranslateY(200);




        //ListView
        listaPersonas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>(){
            @Override
            public void changed(ObservableValue<? extends Usuario> observableValue,Usuario s,Usuario elementoSeleccionado){
                labelUserSelect.setText(elementoSeleccionado.getUsername());
                index = listaPersonas.getSelectionModel().getSelectedIndex();
            }
        });

        listaPersonas.setMaxHeight(300);

        //Ubicación de la ListView
        listaPersonas.setTranslateX(50);
        listaPersonas.setTranslateY(50);




        //Se agregan los elementos a un Pane funcionando como barra superior
        paneSuperior.getChildren().addAll(
                menuButton
        );

        //Se agregan los elementos gráficos a un panel
        paneOpcion1.getChildren().addAll(
                labelAgregarPlatillo,
                textField_platillo,
                textField_calorias,
                textField_precio,
                textField_tiempo,
                botonnAgregarPlato
        );

        //Se agregan elementos gráficos a un panel de opciones
        paneOpcion2.getChildren().addAll(
                labelUserSelect,
                listaPersonas,
                botonEliminarAdministrador,
                botonEditarAdministrador,
                userField_Pane2,
                passWordField_Pane2,
                botonAgregarAdministrador
        );

        //Se agregan todos los Pane a un Pane principal
        paneMain.getChildren().addAll(
                paneSuperior,
                paneOpcion1,
                paneOpcion2
        );

        //Agrego el paneMain al tab2
        tab2.setContent(paneMain);


        //Agrego los Tab al TabPane
        tabPane.getTabs().addAll(  //Agregar los Tab al TabPane
                tab1,
                tab2
        );

        //Se le pide al servidor que envie una lista con la cuál se pueda formar la listView
        cargarListaUsers();

        //Escena donde se mostrará los elementos
        Scene masterApp = new Scene(tabPane,1200,700);
        stage.setResizable(false);
        stage.setTitle("MasterApp");
        stage.setScene(masterApp);
        stage.show();


    }

    public void comprobarLogin(String usuario,String contra){

        Message message = new Message("LoginAdmin",usuario,contra);

        //Escribir al server
        try {
            out.writeObject(message);
            System.out.println("Mensaje enviado");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void agregarNuevoAdmin(String user,String contra){
        //Se crea un mensaje para el server
        Message message = new Message("agregarNuevoAdmin",user,contra);

        try {
            out.writeObject(message);
            System.out.println("Mensaje enviado");
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        personas.add(new Usuario(user,contra));

    }

    public void editarAdministrador(String user,String newUser,String newPassword){

        System.out.println("Entro aqui con: " + user + " " + newUser + " " + newPassword);

        Message message = new Message("editarAdministrador",user,newUser,newPassword);

        try {
            out.writeObject(message);
            System.out.println("Se mando el mensaje");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    //Edita la persona de la listview
    public void editarPersonaLista(String user,String password){
        System.out.println("Entra en editar persona");

        personas.get(index).setUsername(user);
        personas.get(index).setPassword(password);

    }

    public void eliminarAdministrador(String user){

        Message message = new Message("eliminarAdministrador",user);

        try {
            out.writeObject(message);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        personas.remove(index);


    }

    public void crearListView(ListaEnlazada listaEnlazada){

        int size = listaEnlazada.getSize();

        Nodo current = listaEnlazada.getHead();

        for (int i = 0; i < size;i++){

            personas.add(current.getValor());

            current = current.getNext();

        }


        listaEnlazada.verElementos();
    }

    public void cargarListaUsers(){
        Message message = new Message("obtenerListaUsers");

        try {
            out.writeObject(message);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }


    /**
     * Metodo que ejecuta la interfaz de Runnable
     */
    @Override
    public void run(){

        try {
            while (!Thread.currentThread().isInterrupted()){

                //Lee la info del server
                Message message = (Message) in.readObject();

                switch (message.getNombreMetodo()){

                    case "crearListaUsers" -> {
                        crearListView(message.getListaEnlazada());
                    }

                    case "usuario modificado correctamente" -> {
                        editarPersonaLista(message.getUsuario(),message.getPassword());
                    }

                    case "Login_Admin_Exitoso" -> {
                        Platform.runLater(() -> {
                            try {

                                elementosGraficos();

                            }catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                }



            }

        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

    }


    public static void main(String [] args){
        launch(args);
    }


}
