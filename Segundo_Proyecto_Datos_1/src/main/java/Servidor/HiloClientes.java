package Servidor;

import Clases_auxiliares.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;

public class HiloClientes extends Thread{

    //Entrada de mensajes tipo Object
    ObjectInputStream entradaObjects;
    ObjectOutputStream salidaObject;

    Socket socket;

    //Arboles binarios
    ArbolBinario arbolBinarioAdmins;
    ArbolBinario arbolBinarioClient;

    //Lista enlazada
    ListaEnlazada listaEnlazada;

    public HiloClientes(Socket socket,ObjectInputStream entradaObjects,ObjectOutputStream salidaObject) {
        this.socket = socket;

        this.entradaObjects = entradaObjects;
        this.salidaObject = salidaObject;

        System.out.println("Se construye el client handler");
        arbolBinarioAdmins = obtenerListaAdministradores();
        arbolBinarioClient = obtenerListaClientes();

    }

    /**
     * Metodo que se encarga de buscar en un archivo XML la llave que almacena Objetos de tipo "user", después de eso
     * Se encarga de ir Objeto por Objeto obteniendo el valor del "username" y el "password" para asignárselo a un
     * Objeto Usuario que posteriormente será añadido como dato que almacenará un Nodo y será añadido a Lista Enlazada.
     * @return Devuelve la Lista Enlazada creada a partir de Nodos que almacenan objetos de tipo Usuario
     */
    private ArbolBinario obtenerListaClientes() {

        ArbolBinario arbolBinario = new ArbolBinario();
        try {
            //File file = new File("C:\\Users\\josth\\OneDrive\\Documentos\\Proyecto_Datos1_2\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archivos XML\\ClientUsers.xml");

            File file = new File("Archivos XML\\ClientUsers.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            //Obtener lista de elementos con la etiqueta "user"
            NodeList nodeList = document.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String username = element.getElementsByTagName("username").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();

                    //System.out.println("usuario: " + username + "     " + "password: " + password);

                    //Se crea un Objeto usuario con el valor de la contraseña y usuario
                    Usuario usuario = new Usuario(username, password);

                    //Se crea un Objeto nodo
                    Nodo nodo = new Nodo();

                    //Se asigna el valor que va a guardar el Nodo
                    nodo.setValor(usuario);

                    //Se agrega el Nodo a la lista enlazada
                    arbolBinario.insertar(nodo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arbolBinario;
    }

    /**
     * Metodo que se encarga de buscar en un archivo XML la llave que almacena Objetos de tipo "user", después de eso
     * Se encarga de ir Objeto por Objeto obteniendo el valor del "username" y el "password" para asignárselo a un
     * Objeto Usuario que posteriormente será añadido como dato que almacenará un Nodo y será añadido a Lista Enlazada.
     * @return Devuelve la Lista Enlazada creada a partir de Nodos que almacenan objetos de tipo Usuario
     */
    private ArbolBinario obtenerListaAdministradores(){

        ArbolBinario arbolBinario = new ArbolBinario();

        try {
            File file = new File("Archivos XML\\AdminUsers.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            //Obtener lista de elementos con la etiqueta "user"
            NodeList nodeList = document.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String username = element.getElementsByTagName("username").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();

                    //Se crea un Objeto usuario con el valor de la contraseña y usuario
                    Usuario usuario = new Usuario(username,password);

                    //Se crea un Objeto nodo
                    Nodo nodo = new Nodo();

                    //Se asigna el valor que va a guardar el Nodo
                    nodo.setValor(usuario);

                    //Se agrega el Nodo al árbol binario
                    arbolBinario.insertar(nodo);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arbolBinario;

    }

    private void modificarUsuarioAdmin(String username, String newUsername, String newPassword) {
        try {
            File file = new File("Archivos XML\\AdminUsers.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList nodeList = document.getElementsByTagName("user");

            listaEnlazada.verElementos();



            System.out.println("wewewewe" + username + " " + newUsername);

            //Se elimina del árbol binario
            arbolBinarioAdmins.eliminarNodo(username);

            //Se crea un nuevo Nodo
            Nodo nodo = new Nodo();
            Usuario usuario = new Usuario(newUsername,newPassword);
            nodo.setValor(usuario);

            //Se agrega el nuevo nodo con los nuevos valores de usuario y contraseña
            arbolBinarioAdmins.insertar(nodo);

            //Se edita el elemento en la lista enlazada
            listaEnlazada.editarElemento(username,newUsername,newPassword);

            listaEnlazada.verElementos();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String currentUsername = element.getElementsByTagName("username").item(0).getTextContent();

                    if (currentUsername.equals(username)) {
                        Element newUsernameElement = (Element) document.createElement("username");
                        newUsernameElement.appendChild(document.createTextNode(newUsername));
                        element.replaceChild(newUsernameElement, element.getElementsByTagName("username").item(0));

                        Element newPasswordElement = (Element) document.createElement("password");
                        newPasswordElement.appendChild(document.createTextNode(newPassword));
                        element.replaceChild(newPasswordElement, element.getElementsByTagName("password").item(0));

                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        //transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
                        DOMSource source = new DOMSource(document);

                        StreamResult result = new StreamResult(file);
                        transformer.transform(source, result);

                        Message message = new Message("usuario modificado correctamente",newUsername,newPassword);

                        salidaObject.writeObject(message);

                    }
                }
            }

            //System.out.println("El usuario no existe.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Método que se encarga de añadir una persona al XML en formato "username" y "password".
     * El método analiza un archivo de tipo XML, crea un objeto de tipo "user" y unos parámetros de tipo "username" y
     * "password", a los parámetros se les asigna el valor textual pasado en los atributos del método, se hace un salto
     * de línea y al final crea un objeto de tipo "usuario" que será añadido al árbol binario de búsqueda y a una lista
     * enlazada.
     * @param username parámetro de tipo String que contiene el texto de un TextField.
     * @param password parámetro de tipo String que contiene el texto de un TextField.
     */
    private void agregarUsuarioXML(String username,String password){
        try {
            File file = new File("Archivos XML\\AdminUsers.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = (Element) document.getDocumentElement();

            Element user = (Element) document.createElement("user");
            Element usernameElement = (Element) document.createElement("username");
            Element passwordElement = (Element)  document.createElement("password");

            usernameElement.appendChild(document.createTextNode(username));
            passwordElement.appendChild(document.createTextNode(password));

            user.appendChild(usernameElement);
            user.appendChild(passwordElement);

            root.appendChild(user);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            Usuario usuario = new Usuario(username,password);
            Nodo nodo = new Nodo();
            nodo.setValor(usuario);

            listaEnlazada.insertarNuevoNodo(nodo);
            arbolBinarioAdmins.insertar(nodo);

            System.out.println("Usuario agregado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que busca un elemento de tipo String en el árbol binario y lista enlazada eliminándolo.
     * El método analiza un archivo XML que contiene a los usuarios administradores, dentro se busca los objetos con
     * la etiqueta "user", después, va objeto en objeto analizando el atributo "username" verificando que su contenido
     * coincida con el parámetro del método "username", habiendo encontrado un elemento coincidente, elimina el objeto
     * del XML y también del árbol binario así como de la lista enlazada.
     * @param username parámetro de tipo String que indica el objeto que será eliminado del XML.
     */
    private void eliminarUsuarioDeXML(String username) {

        listaEnlazada.eliminarNodo(username);
        arbolBinarioAdmins.eliminarNodo(username);
        boolean existe=  listaEnlazada.devolverElemento(username);
        System.out.println(existe);

        try {
            File file = new File("Archivos XML\\AdminUsers.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = (Element) document.getDocumentElement();
            NodeList nodeList = document.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String usernameElement = element.getElementsByTagName("username").item(0).getTextContent();
                    if (usernameElement.equals(username)) {
                        root.removeChild(node);
                        break;
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new FileOutputStream(file));
            transformer.transform(source, result);


        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarExistenciaAdmins(String user, String password){

        boolean resultado = arbolBinarioAdmins.revisarLogin(user,password);

        if (resultado){
            return true;
        }else {
            System.out.println("No se encontro a la persona");
        }
        //Si llega hasta aquí significa que no hubo coincidencias
        return false;

    }

    private boolean verificarExistenciaClientes(String user, String password){

        boolean resultado = arbolBinarioClient.revisarLogin(user, password);

        if (resultado){
            return true;
        }else {
            System.out.println("No se encontro a la persona");
        }
        //Si llega hasta aquí significa que no hubo coincidencias
        return false;

    }

    private void verificarAdmin(String usuario, String contra){

        boolean existe = verificarExistenciaAdmins(usuario, contra);

        if (existe){
            System.out.println("Se intenta responder al client");
            Message message1 = new Message("Login_Admin_Exitoso");
            try {

                salidaObject.writeObject(message1);

            }catch (IOException e){

                throw new RuntimeException(e);
            }
            System.out.println("Se respondió con éxito");



        }

    }

    private void verificarClients(String usuario,String  contra){

        boolean existe = verificarExistenciaClientes(usuario,contra);

        System.out.println(existe);

        try {

            if (existe) {
                System.out.println("Se intenta responder al client");
                Message message1 = new Message("Login_Client_Exitoso");
                salidaObject.writeObject(message1);
                System.out.println("Se respondió con éxito");
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void obtenerListaUsers(){
        Message message1 = new Message("crearListaUsers");

        //Se genera la lista
        arbolBinarioAdmins.crearListaUsers();
        listaEnlazada = arbolBinarioAdmins.getListaEnlazada();
        message1.setListaEnlazada(listaEnlazada);

        System.out.println("Se trata de responder al masterapp");
        try {

            salidaObject.writeObject(message1);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        System.out.println("Se ha respondido con éxito");
    }


    //Hilo del administrador de clientes
    @Override
    public void run(){

        //Se mantiene el servidor a la escucha de mensajes entrantes
        while (true){

            try {

                Message message = (Message) entradaObjects.readObject();  //Lee un objeto enviado desde el cliente
                System.out.println("Mensaje cliente: " + message.getNombreMetodo());

                switch (message.getNombreMetodo()) {
                    case "LoginAdmin" -> {

                        String usuario = message.getUsuario();
                        String contra = message.getPassword();

                        verificarAdmin(usuario, contra);

                    }
                    case "LoginClient" -> {

                        String usuario = message.getUsuario();
                        String contra = message.getPassword();

                        verificarClients(usuario, contra);

                    }
                    case "obtenerListaUsers" -> obtenerListaUsers();
                    case "editarAdministrador" ->
                            modificarUsuarioAdmin(
                                    message.getUsuario(),
                                    message.getNewUsuario(),
                                    message.getNewPassword());
                    case "eliminarAdministrador" -> eliminarUsuarioDeXML(message.getUsuario());
                    case "agregarNuevoAdmin" -> agregarUsuarioXML(message.getUsuario(), message.getPassword());
                }


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

    }


}
