package Admin_y_Usuario;

import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Clases_auxiliares.Message;
import Clases_auxiliares.Platillo;

import java.io.*;
import java.net.Socket;

public class ClientApp extends Application implements Runnable{

    //Socket del cliente
    Socket socket;

    //Recibir y enviar un Objeto Mensaje
    ObjectOutputStream out;
    ObjectInputStream in;

    //Stage
    Stage stage;



    public ClientApp(){

        try {
            //Inicializa el socket en un host y puerto específico
            this.socket = new Socket("192.168.1.246",1234);

            //Inicializa el envio y entrega de información
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            //Crea un hilo
            Thread hilo = new Thread(this);
            hilo.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Label
    Label labelMain,
    labelCliente;

    //Botones
    Button botonIniciarSesion;

    //Cajas de texto
    TextField usuario;
    PasswordField password;


    @Override
    public void start(Stage primaryStage){

        stage = primaryStage;
        StackPane mainPane = new StackPane();  //StackPane principal
        StackPane loginPane = new StackPane();  //StackPane del login

        //mainPane--------------------------------------------
        //Labels
        labelMain = new Label();
        labelMain.setText("Clientes");
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
        primaryStage.setTitle("ClientApp");
        primaryStage.setScene(menu);

        primaryStage.show();




    }



    //Definir objetos de la interfaz

    //Labels
    Label labelMenu,
            labelInfoPlatillo,
            labelNombre,
            labelCantidadCalorias,
            labelPrecio,
            labelNombrePlatillo,
            labelCantidadCaloriasPlatillo,
            labelPrecioPlatillo;

    //Pane
    Pane mainPane,
            paneMenu;




    //Lista de personas de ejemplo
    ObservableList<Platillo> listaPlatillos = FXCollections.observableArrayList();
    ListView<Platillo> listViewPlatillos = new ListView<>(listaPlatillos);


    public void elementosGraficos() throws IOException {



        mainPane = new Pane();



        //Menu
        labelMenu = new Label();
        labelMenu.setText("Menu de platillos");
        labelMenu.setTranslateX(25);

        Label labelInfoPlatillo = new Label();
        labelInfoPlatillo.setText("Información del platillo");
        labelInfoPlatillo.setTranslateX(300);

        Label labelNombre = new Label();
        labelNombre.setText("Nombre:");
        labelNombre.setTranslateX(300);
        labelNombre.setTranslateY(100);

        Label labelNombrePlatillo = new Label();
        labelNombrePlatillo.setTranslateX(300);
        labelNombrePlatillo.setTranslateY(120);

        labelCantidadCalorias = new Label();
        labelCantidadCalorias.setText("Calorías:");
        labelCantidadCalorias.setTranslateX(300);
        labelCantidadCalorias.setTranslateY(180);

        labelCantidadCaloriasPlatillo = new Label();
        labelCantidadCaloriasPlatillo.setTranslateX(300);
        labelCantidadCaloriasPlatillo.setTranslateY(200);

        labelPrecio = new Label();
        labelPrecio.setText("Precio:");
        labelPrecio.setTranslateX(300);
        labelPrecio.setTranslateY(240);

        labelPrecioPlatillo = new Label();
        labelPrecioPlatillo.setTranslateX(300);
        labelPrecioPlatillo.setTranslateY(260);



        paneMenu = new Pane();
        paneMenu.setStyle("-fx-border-color: #000;-fx-border-width: 2");
        paneMenu.setMinWidth(500);  //Anchura mínima
        paneMenu.setMinHeight(425);  //Altura mínima
        paneMenu.setTranslateX(50);
        paneMenu.setTranslateY(50);



        //Instancias temporales para probar funcionamiento de la listView
        Platillo platillo1 = new Platillo("Arroz con Pollo",2500,1200);
        Platillo platillo2 = new Platillo("Olla de carne",6000,3400);
        Platillo platillo3 = new Platillo("Batido de fresa",1200,2500);
        Platillo platillo4 = new Platillo("Bistec",3000,2360);
        Platillo platillo5 = new Platillo("Cerdo",20000,32520);

        //Añadir los platillos temporales a la lista
        listaPlatillos.addAll(
                platillo1,
                platillo2,
                platillo3,
                platillo4,
                platillo5
        );


        //Detecta la selección de un elemento en la lista
        listViewPlatillos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Platillo>() {
            @Override
            public void changed(ObservableValue<? extends Platillo> observableValue, Platillo s, Platillo elementoSeleccionado) {
                //Hace una acción cuando se selecciona un elemento de la lista
                //Se cambian los textos de los Labels por los del Objeto de la lista seleccionado
                labelNombrePlatillo.setText(elementoSeleccionado.getNombrePlatillo());
                labelCantidadCaloriasPlatillo.setText(String.valueOf(elementoSeleccionado.getCalorias()));
                labelPrecioPlatillo.setText("₡ " + elementoSeleccionado.getPrecio());
            }
        });

        listViewPlatillos.setMaxHeight(375);
        listViewPlatillos.setMinHeight(350);
        listViewPlatillos.setTranslateX(25);
        listViewPlatillos.setTranslateY(25);


        //Se agregan estos elementos al paneMenu
        paneMenu.getChildren().addAll(
                labelMenu,
                labelInfoPlatillo,
                labelNombre,
                labelCantidadCalorias,
                labelPrecio,
                labelNombrePlatillo,
                labelPrecioPlatillo,
                labelCantidadCaloriasPlatillo,
                listViewPlatillos
        );


        //Agrega al Pane principal los demás contenedores
        mainPane.getChildren().addAll(
                paneMenu);


        //Escena donde se mostrará los elementos
        Scene clientApp = new Scene(mainPane,1200,700);
        stage.setTitle("ClientApp");
        stage.setScene(clientApp);
        stage.show();


    }

    private void comprobarLogin(String user,String contra){

        Message message = new Message("LoginClient",user,contra);

        try {
            out.writeObject(message);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ejecutarInterfaz(){
        //Se manda a crear una lista de platillos que son tomadas del json correspondiente
        solicitarListaPlatillos();
        try {

            elementosGraficos();

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void solicitarListaPlatillos(){
        //System.out.println("solicitarLitaPlatillo");
        try {

            Message message = new Message("obtenerListaPlatillos");

            out.writeObject(message);
            System.out.println("Se manda mensaje al Servidor");





        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que se ejecuta constantemente a la espera de algún mensaje por parte del servidor
     */
    @Override
    public void run(){

        try {

            while (true){

                //Leer info del server
                Message message = (Message) in.readObject();

                System.out.println("Mensaje del server: " + message.getNombreMetodo());

                switch (message.getNombreMetodo()){

                    case "Login_Client_Exitoso" -> {
                        Platform.runLater(this::ejecutarInterfaz);
                    }

                    case "Login_Client_Fallido" -> {
                        System.out.println("pipipipi");
                    }

                }


            }








        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args){
        launch(args);
    }


}
