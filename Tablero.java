//@ refine "Tablero.jml";

import java.util.Vector;

public class Tablero {
	//Constantes
	private static final String ERROR1 = "Dimensiones incorrectas.";
	private static final String ERROR2 = "Posición incorrecta.";
	//Solo se puede usar atributos privados en esta clase
	private /*@ spec_public @*/ Object[][] tablero;
	private /*@ spec_public @*/ boolean circular;
	private /*@ spec_public @*/ int xMax;
	private /*@ spec_public @*/ int yMax;

	/*@ pure @*/ public Tablero(int numFil, int numCol, boolean circ) throws TableroException {//Constructor de la clase
	
		if ((numFil < 5) || (numCol < 5)) {
			throw new TableroException(ERROR1);
		}
		
		tablero = new Object[numFil][numCol];
		circular = circ;
		xMax = numFil;
		yMax = numCol;
		int i,j;
		for (i = 0; i < numFil; i++)
			for (j = 0; j < numCol; j++) {
				tablero[i][j] = new Boolean(false);
			}
	}//Fin del constructor de la clase Tablero

	/*@ pure @*/ public Object obtenerElemento(int fil, int col) throws TableroException {
		//Método que devuelve el elemento situado en una determinada posición del tablero
		if ((fil < 0) || (fil > (xMax-1)) || (col < 0) || (col > (yMax-1))) {
			throw new TableroException(ERROR1);
		}
		
		return(tablero[fil][col]);
	}//Fin del método obtenerElemento

	/*@ pure @*/ public void insertarElemento(int fil, int col, Object elemento) throws TableroException {
	//Método que inserta un elemento en una posición del tablero
	//Insertar aqui en las filas de fuera si el tablero es circular
	//Cambiando el elemento en si
		if ((fil < 1) || (fil > (xMax-2)) || (col < 1) || (col > (yMax-2))) {
			throw new TableroException(ERROR1);
		}
		
		tablero[fil][col] = elemento;
		//Compronado las filas
		if (circular) {
			if (fil == 1) {
				tablero[xMax-1][col] = elemento;
			if (col == 1)
				tablero[xMax-1][yMax-1] = elemento;
			else
				if (col == yMax-2)
					tablero[xMax-1][0] = elemento;
				} else
					if (fil == xMax - 2) {
							tablero[0][col] = elemento;
						if (col == yMax-2)
							tablero[0][0] = elemento;
						else
							if (col == 1)
								tablero[0][yMax-1] = elemento;
					}
					//Comprobando las columnas
					if (col == 1)
						tablero[fil][yMax-1] = elemento;
					else
						if (col == yMax-2)
							tablero[fil][0] = elemento;
		}
		return;
	}//Fin del método insertarElemento

	/*@ pure @*/ public Vector obtenerVecinos(int fil, int col) throws TableroException {
	//Método que devuelve un conjunto de objetos que son los vecinos de una determinada posición
	//Para obtener estos vecinos habrá que tener en cuenta si el tablero es circular o no
		if ((fil < 1) || (fil > (xMax-2)) || (col < 1) || (col > (yMax-2))) {
			throw new TableroException(ERROR1);
		}

		Vector vecinos = new Vector(9, 0);
		int i,j;
		for (i = -1; i <= 1; i++)
			for (j = -1; j<= 1; j++)
				vecinos.add(tablero[fil+i][col+j]);

		return(vecinos);
	}//Fin del método obtenerVecinos

}//Fin de la clase Tablero
