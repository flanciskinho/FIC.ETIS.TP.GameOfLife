import java.io.*;
import java.util.*;

public class Menu {

	private static final int SLEEP = 2222; //CTE PARA QUE PARE EN ALGUN PROCESO PARA PODER VER EL RESULTADO
	
	private static void clearScreen() {
	//Método que intenta simular un limpiado de pantalla

		try {
			final String os = System.getProperty("os.name");

			if (os.contains("Windows"))
				Runtime.getRuntime().exec("cls");
			else
				Runtime.getRuntime().exec("ls");
		} catch (Exception e) {
			for (int i = 0; i < 45; i++)
				System.out.println("\n\n\n\n");	
		}
	}

	private static int menu1() {
	//Método que muestra al usuario un menu;

		int opcion = 0;
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
		
		do {
			clearScreen();//CLEARSCREEN
			System.out.println("\t//////////////////////////////////////////////////");
			System.out.println("\t//  1 -> Establecer tiempo entre cada ciclo.    //");
			System.out.println("\t//  2 -> Establecer el número máximo de ciclos. //");
			System.out.println("\t//  3 -> Crear un Juego nuevo.                  //");
			System.out.println("\t//  4 -> Abandonar el juego.                    //");
			System.out.println("\t//////////////////////////////////////////////////");
		
			try {
				opcion = new Integer(entrada.readLine()).intValue();
			} catch (Exception ex) {
				System.out.println("Error en campo.");
			}
		} while ((opcion < 1) || (opcion > 4));
		
		return(opcion);
	}
   
	private static int menu2() {
	//Método que le muestra al usuario un menu
	
		int opcion = 0;
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
		
		do {
			clearScreen();//CLEARSCREEN
			System.out.println("\t/////////////////////////////////////////////");
			System.out.println("\t//  1 -> Cambiar el estado de una célula.  //");
			System.out.println("\t//  2 -> Insertar varias células.          //");
			System.out.println("\t//  3 -> Mostrar el estado del tablero.    //");
			System.out.println("\t//  4 -> Empezar el juego.                 //");
			System.out.println("\t//  5 -> Abandonar el juego.               //");
			System.out.println("\t/////////////////////////////////////////////");
			
			try {
				opcion = new Integer(entrada.readLine()).intValue();
			} catch (Exception ex) {
				System.out.println("Error en campo.");
			}
		} while ((opcion < 1) || (opcion > 5));

		return(opcion);
	}

	private static int pedirTiempo() {
		int tiempo = -1;
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
		
		do {
			clearScreen();//CLEARSCREEN
			System.out.println("///////////////////////////////////////////////////////////////////");
			System.out.println("//Establezca el tiempo que considere necesario entre cada ciclo. //");
			System.out.println("//        (Introduzca el tiempo en decimas de segundo)           //");
			System.out.println("//        (No se puede establecer tiempo negativo)               //");
			System.out.println("///////////////////////////////////////////////////////////////////");

			try {
				tiempo = new Integer(entrada.readLine()).intValue();
			} catch (Exception ex) {
				System.out.println("Error en campo.");
			}
		} while (tiempo < 0);
		
		return(tiempo);
	}
   
	private static int pedirCiclos() {
		int ciclos = -1;
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
		
		do {
			clearScreen();//CLEARSCREEN
			System.out.println("///////////////////////////////////////////////////////////////");
			System.out.println("//Establezca el número máximo de ciclos que tendra el juego. //");
			System.out.println("//        (Introduzca un número positivo (x>0))              //");
			System.out.println("///////////////////////////////////////////////////////////////");
			
			try {
				ciclos = new Integer(entrada.readLine()).intValue();
			} catch (Exception ex) {
				System.out.println("Error en campo.");
			}
		} while (ciclos <= 0);
		
		return(ciclos);
	}

	private static int pedirTamanho(String cosa) {
		int size = -1;
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
		
		do {
			clearScreen();//CLEARSCREEN
			System.out.println("Establezca el tamaño de la" + cosa + "del tablero:");
			System.out.println("(Introduzca un número positivo (x>0))");
			
			try {
				size = new Integer(entrada.readLine()).intValue();
			} catch (Exception ex) {
				System.out.println("Error en campo.");
			}
		} while (size < 1);

		return(size);
	}

   private static boolean esCircular() {
		char ch = 'x';
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
      
		do {
			clearScreen();//CLEARSCREEN
			System.out.println("Desea que el tablero tenga forma de toroide:");
			System.out.println("(S/N)");
			
			try {
				ch =  (char) entrada.read();
			} catch (Exception ex) {}
		} while ((ch != 'S') && (ch != 'N') && (ch != 's') && (ch != 'n'));
		
		if ((ch == 'S') || (ch == 's'))
			return(true);
		else
			return(false);
	}

	private static boolean abandonar() {
		char ch = 'x';
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
		
		do {
			System.out.println("Desea abandonar el juego:");
			System.out.println("(S/N)");
			try {
				ch =  (char) entrada.read();
			} catch (Exception ex) {}
	
			clearScreen();//CLEARSCREEN
		} while ((ch != 'S') && (ch != 'N') && (ch != 's') && (ch != 'n'));
		
		if ((ch == 'S') || (ch == 's'))
			return(true);
		else
			return(false);
	}
   
   private static void insertarCelulas(JuegoVida game) {
		int size = -1;
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
		Vector fil = new Vector();
		Vector col = new Vector();
		
		while (size < 0) {
			System.out.println("Inserte el número de células que dese insertar:");
			System.out.println("(x>=0)");
			
			try {
				size = new Integer(entrada.readLine()).intValue();
			} catch (Exception ex) {System.out.println("Error en campo.");}
		}
		
		for (int i = 0; i < size; i++) {
			clearScreen();//CLEARSCREEN
			System.out.println("Puede repetir casillas si lo desea.");
			
			col.add( new Integer(pedirCoordenada(" X")));
			fil.add( new Integer(pedirCoordenada(" Y")));
		}
		
		try {
			game.insertarCelulas(fil,col);
			mostrarTablero(game);
		} catch (JuegoVidaException ex) {}
	}

	/*@ nullable @*/ private static JuegoVida crearJuego() {
		JuegoVida Game = null;
		boolean circular = false;
		int fil, col;
		fil = col = 0;
		
		fil = pedirTamanho(" fila ");
		col = pedirTamanho(" columna ");
		circular = esCircular();
		try {
			Game = new JuegoVida(fil, col, circular);
		} catch (JuegoVidaException ex) {
			Game = null;
		}
		
		return(Game);
	}

   private static int pedirCoordenada(String coordenada) {
		//Método que pide al usuario una coordenada y la devuelve
		int number = 0;
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));
		
		while (number < 1) {
			System.out.println("Inserte el valor de la coordenada" + coordenada +
				":\n(" + coordenada + "> 0 )");
				
			try {
				number = new Integer(entrada.readLine()).intValue();
			} catch (Exception e) {
				System.err.println("Error en campo.");
			}
		}
		
		return(number);
   }

	private static void mostrarTablero(JuegoVida game) {
		clearScreen();//CLEARSCREEN
		
		game.mostrarCuadricula();
		try {
			Thread.sleep(SLEEP);
		} catch (Exception ex) {}
		
		return;
	}

	public static void main (String [] args) {
	//Variables

		JuegoVida game = null;
		int ciclos, cnt, tiempo, opcion, x, y;
		boolean continuar, abandonar;

		//Inicialización de variables
		tiempo    = 5;
		ciclos    = 10;
		abandonar = false;//Variable que indica que el jugador quiere abandonar el juego

		do {
			continuar = true;
         
			while (continuar) {
				clearScreen();//CLEARSCREEN
				
				opcion = menu1();
				switch (opcion) {
					case 1: tiempo = pedirTiempo();
						break;
					case 2: ciclos = pedirCiclos();
						break;
					case 3: game = crearJuego();
						if (game != null)
							continuar = false;
						else
							System.out.println("No se ha podido crear el Juego.\nPorfavor intentelo de nuevo.");
						break;
					case 4: continuar = false;
						abandonar = true;
						break;
				}
			}

			continuar = true;
			while ((continuar) && (!abandonar)) {
				opcion = menu2();

				switch (opcion) {
					case 1: x = pedirCoordenada(" X");
						y = pedirCoordenada(" Y");
						try {
							game.cambiarEstado(y,x);
							mostrarTablero(game);
						} catch (JuegoVidaException ex) {}
						break;
					case 2: insertarCelulas(game);
						break;
					case 3: mostrarTablero(game);
						break;
					case 4: continuar = false;
						break;
					case 5: continuar = false;
						abandonar = true;
						break;
				}
			}
         
			cnt = 0;
			while ( (!abandonar) && (ciclos!=cnt) && (!game.juegoBloqueado())) {
				cnt++;
				clearScreen();//CLEARSCREEN
				
				System.out.println("Ciclo número " + cnt + ".\nEstado del juego:");
				game.mostrarCuadricula();
				try {
					Thread.sleep(tiempo*100);
				} catch (Exception ex) {}
				game.evolucionar();
			}
    
			if (!abandonar) {
				//CLEARSCREEN
				mostrarTablero(game);
				System.out.println("\n(Es el estado final del tablero tras " + cnt + " ciclos.)\n");
				abandonar = abandonar();
			}
		} while (!abandonar);
	}

}
