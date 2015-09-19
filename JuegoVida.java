//@ refine "JuegoVida.jml";

import java.util.Vector; //Para el Vector

public class JuegoVida {

	//Constantes: se pueden añadir constantes si es necesario
	private static final String ERROR1 = "Dimensiones incorrectas.";
	private static final String ERROR2 = "Posición incorrecta.";
	private static final String ERROR3 = "Los vectores de posiciones tienen diferente longitud.";
	private static final String ERROR4 = "Posición repetida.";
	
	private static final int	 NORMAL = 1;// 0-> tablero entero, 1-> tablero para el jugador
	
	//Solo se pueden usar atributos privados en esta clase
	private /*@ spec_public @*/ int xMax;//Posición 'x' Máxima del tablero
	private /*@ spec_public @*/ int yMax;//Posición 'y' Máxima del tablero
	private /*@ spec_public @*/ Tablero board;	//Tablero principal del juego
	private /*@ spec_public @*/ Tablero boardAux;//Tablero auxiliar del juego

	/*@ pure @*/ public JuegoVida(int numFil, int numCol, boolean circ) throws JuegoVidaException {
	
		if ((numFil < 3) || (numCol) < 3) {
			throw new JuegoVidaException(ERROR1);
		}
		
		xMax	  = numFil + 2;
		yMax	  = numCol + 2;
		
		try {
			board	 = new Tablero(xMax, yMax, circ);
			boardAux = new Tablero(xMax, yMax, circ);
		} catch (TableroException ex) {}
	}//Fin del constructor de la clase JuegoVida

	/*@ pure @*/ public void borrarCuadricula() {
	//Método que borra todos los elementos del juego
		int i,j;
		
		try {
			for (i = 1; i < xMax - 1; i++)
				for (j = 1; j < yMax -1; j++)
					board.insertarElemento(i, j, new Boolean(false));
		} catch (TableroException ex) {}
		
		return;
	}//Fin del método borrarCuadricula

	/*@ pure @*/ public int contarVecinos(int fil, int col) throws JuegoVidaException {
	//Método que devuelve el nÃºmero de vecinos, de un determinado elemento, que se encuentran en un estado vivo
	
		if ((fil < 1) || (col < 1) || (fil > (xMax -2)) || (col > (yMax -2)) || (boardAux == null)) {
			throw new JuegoVidaException(ERROR2);
		}
		
		int numVecinos = 0;
		
		try {
		  Vector vecinos = boardAux.obtenerVecinos(fil, col);
		  for (int i = 0; i < vecinos.size(); i++)
			  if ( ((Boolean) vecinos.get(i)).booleanValue() == true)
				  numVecinos++;
			if (celulaViva(fil, col))
				numVecinos--;
		} catch (TableroException ex) {}
		
		return(numVecinos);
	}//Fin del método contarVecinos

	/*@ pure @*/ public void insertarCelula(int fil, int col) throws JuegoVidaException {
	//Método que inserta en el juego un nuevo elemento en una determinada posición
	
		if ((fil < 1) || (col < 1) || (fil > (xMax -2)) || (col > (yMax -2)) || (board == null)) {
			throw new JuegoVidaException(ERROR2);
		}
		
		try {
			board.insertarElemento(fil, col, new Boolean(true));
		} catch (TableroException ex) {}
		
		return;
	}//Fin del método insertarCelula

	/*@ pure @*/ public void insertarCelulas(Vector fil, Vector col) throws JuegoVidaException {
	//Método que inserta en el juego un conjunto de elementos en unas determinadas posiciones
	
		if ((fil.size() != col.size()) || (fil == null) || (col == null) || (board == null)) {
			throw new JuegoVidaException(ERROR3);
		}
		
		for (int i = 0; i< fil.size(); i++)
			if ((((Integer) fil.get(i)).intValue() >= xMax-1) ||
				(((Integer) fil.get(i)).intValue() <= 0) || 
				(((Integer) col.get(i)).intValue() >= yMax-1) ||
				(((Integer) col.get(i)).intValue() <= 0)) {
					throw new JuegoVidaException(ERROR2);
				}
				
		try {
			for (int i = 0; i < fil.size(); i++)
				insertarCelula(((Integer) fil.get(i)).intValue(), ((Integer) col.get(i)).intValue());
		} catch (JuegoVidaException ex) {}
		
		return;
	}//Fin del método insertarCelulas

	/*@ pure @*/ public boolean celulaViva(int fil, int col) throws JuegoVidaException{
	//Método que devuelve un valor lógico indicando si una célula esta viva o no
	
		if ((fil < 1) || (col < 1) || (fil > (xMax -2)) || (col > (yMax -2)) || (boardAux == null)) {
			throw new JuegoVidaException(ERROR2);
		}
		
		boolean celula = false;
		try {
			celula =(((Boolean) boardAux.obtenerElemento(fil, col)).booleanValue());
		} catch (TableroException ex) {}
		
		return(celula);
	}//Fin del método celulaViva

	/*@ pure @*/ public void cambiarEstado(int fil, int col) throws JuegoVidaException {
	//Método que cambia el estado de una célula
	
		if ((fil < 1) || (col < 1) || (fil > (xMax -2)) || (col > (yMax -2)) || (board == null)) {
			throw new JuegoVidaException(ERROR2);
		}
		
		try {
			if (((Boolean) board.obtenerElemento(fil, col)).booleanValue())
				board.insertarElemento(fil, col, new Boolean(false));
			else
				board.insertarElemento(fil, col, new Boolean(true));
		} catch (TableroException ex) {}
		
		return;
	}//Fin del método cambiarEstado

	/*@ pure @*/ public void evolucionar() {
	//Método que cambia el estado de todos los elementos de la cuadrícula en función de las reglas del juego
	
		//Primero: copiar lo de un tablero en otro
		int i,j,numVecinos;
		try {
			for (i = 1; i < (xMax - 1); i++)
				for (j = 1; j < (yMax - 1); j++)
					boardAux.insertarElemento(i, j, board.obtenerElemento(i, j));//Pongo el elemento de uno en la posicion del otro
		} catch (TableroException ex) {}
		
		//Segundo: uso un tablero para dar vida
		borrarCuadricula();//Pongo el tablero auxiliar con todos muertos
		try {
			for (i = 1; i < (xMax - 1); i++)
				for (j = 1; j < (yMax - 1); j++) {
					numVecinos = contarVecinos(i, j);
					switch (numVecinos) {
						case 2: if (!celulaViva(i,j))
							break;
						case 3: insertarCelula(i,j);
							break;
					}
				}
		}
		catch (JuegoVidaException ex) {}
		
		return;
	}//Fin del método evolucionar

	/*@ pure @*/ public boolean juegoBloqueado() {
	//Método que indica si el juego no cambia de estado de un turno a otro
	
		int i,j;
		try {
			for (i = 1; i < xMax - 1; i++)
				for (j = 1; j < yMax - 1; j++)
					if ( ((Boolean)boardAux.obtenerElemento(i, j)).booleanValue() != ((Boolean)board.obtenerElemento(i, j)).booleanValue())
						return(false);
						
		} catch (TableroException ex) {}
		
		return(true);
	}//Fin del método juegoBloqueado

	public void mostrarCuadricula() {
	//Método que muestra por pantalla la cuadrícula con los elementos actuales.
	//No hay que realizar especificación JML para este método
		int i, j;
		int ini, xFin, yFin;
		ini = NORMAL;
		xFin = xMax -NORMAL;
		yFin = yMax -NORMAL;
		System.out.print("  ");
		for (j = ini; j < yFin; j++)
			if (j < 10)
				System.out.print(j + " ");
			else
				System.out.print(j);
		System.out.println();
		
		for (i = ini; i < xFin; i++) {
			System.out.print(i);
			if (i<10)
				System.out.print(" ");
			try {
				for (j = ini; j < yFin; j++)
					if ( ((Boolean)board.obtenerElemento(i, j)).booleanValue() == true)
						System.out.print("X ");
					else
						System.out.print("· ");
			} catch (TableroException ex) {}
			System.out.println();
		}
		return;
	}//Fin del método mostrarCuadricula

}//Fin de la clase JuegoVida
