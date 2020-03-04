import es.javier.Launcher;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
//import org.testfx.framework.junit.ApplicationTest;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TableViewMatchers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class TestLogin extends ApplicationTest {

    @Before
    public void setup() throws Exception {
        ApplicationTest.launch(Launcher.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    /**
     * Test que sirve para verificar que el texto del botón cuyo id es #btnNuevo es "Nuevo"
     */
    @Test
    public void textoDespues() {
        verifyThat("#btnNuevo", hasText("Nuevo"));
    }

    /**
     * Test que sirve para verificar que, al abrir el stage de los estilos, verificar que cambie el texto del botón que abre ese stage
     * (los métodos correspondientes al combobox realmente no importan para pasar o no el test)
     */
    @Test
    public void cambioEstilo(){
        clickOn("#btnEstilos");
        clickOn("#comboBox");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnEstilos", hasText("Nuevo estilo"));
    }

    //Si me logueo con pruebajavi98v2@gmail.com - Holahola1
    /**
     * test que comprueba que la tabla tiene 7 filas
     */
    @Test
    public void primeraFila(){
        verifyThat("#tableMessages",TableViewMatchers.hasNumRows(7));
    }

    //también hay que loguearse con pruebajavi98v2@gmail.com - Holahola1

    /**
     * Test que comprueba que la sexta fila de la tabla tiene el contenido indicado, aún escribiendo un nuevo mensaje
     */
    @Test
    public void insercionFila(){
        clickOn("#btnNuevo");
        clickOn("#cb_remitente");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#tf_para");
        write("pruebajavi98@gmail.com");
        clickOn("#tf_asunto");
        write("Prueba test");
        clickOn("#ta_contenido");
        write("Hola");
        clickOn("#btn_enviar");
        verifyThat("#tableMessages",TableViewMatchers.containsRowAtIndex(6, new String[]{"pruebajavi98v2@gmail.com", "prueba de reeeeeenvio", "10/12/19"}));
    }

}
