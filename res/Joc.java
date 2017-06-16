import java.util.*;
import java.io.*;
import java.text.*;
import java.time.*;
import java.nio.file.Files;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.sql.*;

class Tirada implements Serializable {
	byte[] entrada, malPosicionados, bienPosicionados, numeroAleatorio;
	int id; //comentario
	
	public Tirada(byte[] entrada, byte[] numeroAleatorio) {
		this.entrada = entrada;
		this.numeroAleatorio = numeroAleatorio;
	}
	
	public byte[] getEntrada() {
		byte[] t = this.entrada;
		return t;
	}
	public String getEntradaString() {
		StringBuffer res = new StringBuffer();
		byte[] t;
		for (int i = 0; i < (t = getEntrada()).length; i++) res.append(t[i]);
		return res.toString();
	}
	public byte[] getMalPosicionados() {
		byte[] t = this.malPosicionados;
		return t;
	}
	public void crearMalPosicionados() {
		byte[] entrada = this.entrada;
		byte[] numeroAleatorio = this.numeroAleatorio;
		byte[] bienPosicionados = this.bienPosicionados;
		byte[] malPosicionados = {0,0,0,0,0};
		
		for (int i = 0; i < entrada.length; i++) {
			for (int j = 0; j < numeroAleatorio.length; j++) {
				if (entrada[i] == numeroAleatorio[j] && bienPosicionados[i] != 1) {
					malPosicionados[i] = 1;
				}
			} 
		}
		
		this.malPosicionados = malPosicionados;
	}
	public byte[] getBienPosicionados() {
		byte[] t = this.bienPosicionados;
		return t;
	}
	public void crearBienPosicionados() {
		byte[] entrada = this.entrada;
		byte[] numeroAleatorio = this.numeroAleatorio;
		byte[] bienPosicionados = {0,0,0,0,0};
		
		for (int i = 0; i < entrada.length; i++) {
			if (entrada[i] == numeroAleatorio[i]) {
				bienPosicionados[i] = 1;
			}
		}
		
		this.bienPosicionados = bienPosicionados;
	}
	
	public int getCantidadBienPosicionados() {
		byte[] t = getBienPosicionados();
		int cantidad = 0;
		
		for (int i = 0; i < t.length; i++) {
			if (t[i] == 1) cantidad++;
		}
		
		return cantidad;
	}
	public int getCantidadMalPosicionados() {
		byte[] t = getMalPosicionados();
		int cantidad = 0;
		
		for (int i = 0; i < t.length; i++) {
			if (t[i] == 1) cantidad++;
		}
		
		return cantidad;
	}
	
	public String getBienPosicionadosString() {
		StringBuffer res = new StringBuffer();
		byte[] t;
		for (int i = 0; i < (t = getBienPosicionados()).length; i++) res.append(t[i]);
		return res.toString();
	}
	public String getMalPosicionadosString() {
		StringBuffer res = new StringBuffer();
		byte[] t;
		for (int i = 0; i < (t = getMalPosicionados()).length; i++) res.append(t[i]);
		return res.toString();
	}
	
	byte[] getNumeroAleatorio() {
		byte[] res = numeroAleatorio;
		return res;
	}
	
	public int getId() {return this.id;}
	public void setId(int id) {this.id = id;}
	
	public String toString() {
		StringBuffer entrada, malPosicionados, bienPosicionados;
		
		entrada = new StringBuffer();
		for (int i = 0; i < this.entrada.length; i++) {
			entrada.append(this.entrada[i]);
		}
		
		malPosicionados = new StringBuffer();
		for (int i = 0; i < this.malPosicionados.length; i++) {
			malPosicionados.append(this.malPosicionados[i]);
		}
		
		bienPosicionados = new StringBuffer();
		for (int i = 0; i < this.bienPosicionados.length; i++) {
			bienPosicionados.append(this.bienPosicionados[i]);
		}
		
		
		return "NT: " + entrada.toString() + " BP: " + bienPosicionados.toString() + " MP: " + malPosicionados.toString();
	}
}

class PartidaAvanzada extends Partida implements Serializable {
	
	byte cantidadVidas;
	
	
	public PartidaAvanzada() {
		setCantidadVidas((byte)10);
		setVidas(getCantidadVidas());
	}
	
	public byte getCantidadVidas() {return this.cantidadVidas;}
	public void setCantidadVidas(byte cantidadVidas) {this.cantidadVidas = cantidadVidas;}
	
	public void setPartidaAcabada() {
		int bienPosicionados = getUltimaTirada().getCantidadBienPosicionados();
		if (bienPosicionados == 5 || getCantidadVidas() < 1) this.partidaAcabada = true;
		else {
			this.partidaAcabada = false;
			cantidadVidas--;
		}
	}
}

class PartidaPrincipiante extends Partida implements Serializable {
	
	public PartidaPrincipiante() {}
	
	public void setPartidaAcabada() {
		int bienPosicionados = getUltimaTirada().getCantidadBienPosicionados();
		if (bienPosicionados == 5) this.partidaAcabada = true;
		else this.partidaAcabada = false;
	}
	
}

abstract class Partida implements Serializable {
	
	byte[] numeroAleatorio;
	java.util.List<Tirada> tiradas = new java.util.ArrayList<Tirada>();
	boolean partidaAcabada = false;
	String nick = "Anonymous";
	String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	byte vidas = 0;
	int id;
	
	public Partida() {}
	
	public byte[] getNumeroAleatorio() {
		byte[] t = this.numeroAleatorio;
		return t;
	}
	public String getNumeroAleatorioString() {
		StringBuffer res = new StringBuffer();
		byte[] t;
		for (int i = 0; i < (t = getNumeroAleatorio()).length; i++) res.append(t[i]);
		return res.toString();
	}
	
	public Tirada getUltimaTirada() {return getTiradas().get((getTiradas().size()) - 1);}
	public java.util.List<Tirada> getTiradas() {return new java.util.ArrayList<>(this.tiradas);}
	
	public boolean getPartidaAcabada() {return this.partidaAcabada;}
	protected abstract void setPartidaAcabada();
	
	public String getNick() {return this.nick;}
	public void setNick(String nick) {this.nick = nick;
		}
	public String getFecha() {return this.fecha;}
	
	public byte getVidas() {return this.vidas;}
	public void setVidas(byte vidas) {this.vidas = vidas;}
	
	public int getId() {return this.id;}
	public void setId(int id) {this.id = id;}
	
	public void crearNumeroAleatorio() {
		byte numeroAleatorio[] = new byte[5];
		String numero = String.valueOf(new Random().nextInt(99999 - 0) + 0);
		StringBuffer sb = new StringBuffer(numero);
		
		while (sb.length() < 5) sb.insert(0, '0');
		
		numero = sb.toString();
		
		for (int i = 0; i < numero.length(); i++) numeroAleatorio[i] = Byte.parseByte("" + numero.charAt(i));
		
		this.numeroAleatorio = numeroAleatorio;
	}
	
	protected void addTirada(Tirada tirada) {
		this.tiradas.add(tirada);
	}
	
	public String toString() {
		StringBuffer sbPartida;
		int contador = 1;
		
		sbPartida = new StringBuffer();
		
		sbPartida.append("Jugador: " + getNick() + "\n");
		sbPartida.append("Fecha:   " + getFecha() + "\n");
		for (Iterator iterator = tiradas.iterator(); iterator.hasNext();) {
			sbPartida.append(iterator.next().toString() + " #" + contador + "\n");
			contador++;
		}
		
		return sbPartida.toString();
	}
}

//~ class FrameBienvenida {
	//~ JFrame frame = new JFrame("Bienvenid@ - MasterMind 1.2");
	//~ JPanel panel;
	//~ JLabel labelWelcome = new JLabel("WELCOME TO MASTERMIND!");
	//~ ButtonGroup radiosPartida = new ButtonGroup();
	//~ JRadioButton radioPartidaPrincipiante = new JRadioButton("Principiante", true);
	//~ JRadioButton radioPartidaAvanzada = new JRadioButton("Avanzada", true);
	//~ JButton buttonNuevaPartida = new JButton("Nueva partida");
	
	//~ public FrameBienvenida() {
		//~ frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//~ frame.setSize(400, 200);
		//~ JPanel panel = (JPanel)frame.getContentPane();
		//~ panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		//~ labelWelcome.setFont(new Font("Arial", Font.PLAIN, 16));
		
		//~ radiosPartida.add(radioPartidaPrincipiante);
		//~ radiosPartida.add(radioPartidaAvanzada);
		
		//~ panel.add(labelWelcome);
		//~ panel.add(radioPartidaPrincipiante);
		//~ panel.add(radioPartidaAvanzada);
		//~ panel.add(buttonNuevaPartida);
		
		//~ buttonNuevaPartida.addActionListener(new PartidaListener());
		
		//~ frame.setLocationRelativeTo(null);
		//~ frame.setResizable(false);
		//~ frame.setVisible(true);
	//~ }
	
	//~ class PartidaListener implements ActionListener {
		//~ public void actionPerformed(ActionEvent e) {
			//~ if (radioPartidaPrincipiante.isSelected()) Joc.nuevaPartida(new PartidaPrincipiante());
			//~ if (radioPartidaAvanzada.isSelected()) Joc.nuevaPartida(new PartidaAvanzada());
			//~ System.exit(0);
		//~ }
	//~ }
//~ }

class JocFrame {
	
	Vector<Vector> rowData = null;
	Vector<String> columnNames = null;
	
	JFrame frame = new JFrame("MasterMind 1.2");
	JPanel panel;
	JMenuBar menuBar = new JMenuBar();
	JMenu menuNuevaPartida = new JMenu("Nueva partida");
	JMenuItem itemNuevaPartidaPrincipiante = new JMenuItem("Principiante");
	JMenuItem itemNuevaPartidaAvanzada = new JMenuItem("Avanzada");
	
	JLabel labelTitulo = new JLabel("MASTERMIND");
	JPanel panelTitulo = new JPanel(new BorderLayout());
	
	JPanel panelTiradas = new JPanel();
	JButton buttonGuardarPartida = new JButton("Guardar partida");
	JTable tableTiradas;
	DefaultTableModel dmTableTiradas;
	JScrollPane scrollTableTiradas = new JScrollPane(tableTiradas);
	JTextField textFieldEntrada = new JTextField();
	JButton buttonEntrada = new JButton("Entrar");
	
	JPanel panelPartidas = new JPanel();
	JLabel labelPartidasGuardadas = new JLabel("Partidas Guardadas");
	JScrollPane scrollTablePartidas;
	JTextField textFieldPartidas = new JTextField();
	JButton buttonCargarPartida = new JButton("Entrar");
	
	Partida partida;
	
	public JocFrame() {
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);
		
		
		menuBar.add(menuNuevaPartida);
		menuNuevaPartida.add(itemNuevaPartidaPrincipiante);
		menuNuevaPartida.add(itemNuevaPartidaAvanzada);
		
		JPanel panelEntrada = new JPanel();
		panelEntrada.setLayout(new BorderLayout());
		panelEntrada.add(textFieldEntrada, BorderLayout.CENTER);
		panelEntrada.add(buttonEntrada, BorderLayout.EAST);
		
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBotones.add(buttonGuardarPartida);
		
		panelTitulo.add(labelTitulo, BorderLayout.CENTER);
		
		panelTiradas.setLayout(new BorderLayout());
		panelTiradas.add(panelBotones, BorderLayout.NORTH);
		panelTiradas.add(panelEntrada, BorderLayout.SOUTH);
		
		panelPartidas.setLayout(new BorderLayout());
		scrollTablePartidas = new JScrollPane(getTablaPartidas());
		panelPartidas.add(scrollTablePartidas, BorderLayout.CENTER);
		JPanel panelSeleccionarPartida = new JPanel(new FlowLayout());
		panelSeleccionarPartida.add(textFieldPartidas);
		panelSeleccionarPartida.add(buttonCargarPartida);
		panelPartidas.add(panelSeleccionarPartida, BorderLayout.SOUTH);
		
		Box boxPartida = Box.createHorizontalBox();
		boxPartida.add(panelTiradas);
		boxPartida.add(panelPartidas);
		JPanel panelPartida = new JPanel();
		panelPartida.add(boxPartida);
		
		Box box = Box.createVerticalBox();
		box.add(panelTitulo);
		box.add(panelPartida);
		panel = (JPanel)frame.getContentPane();
		panel.add(box);
		
		itemNuevaPartidaPrincipiante.addActionListener(new NuevaPartidaPrincipianteListener());
		itemNuevaPartidaAvanzada.addActionListener(new NuevaPartidaAvanzadaListener());
		buttonGuardarPartida.addActionListener(new GuardarPartidaListener());
		//itemCargarPartida.addActionListener(new CargarPartidaListener());
		buttonEntrada.addActionListener(new EnviarEntradaListener());
		
		frame.setJMenuBar(menuBar);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}
	
	Partida getPartida() {
		return this.partida;
	}
	
	JTable getTablaPartidas() {
		String URL = "jdbc:mysql://localhost:3307/mastermind", USER = "root", PWD = "";
		String querySelect ="select partidas.nick, partidas.fecha, partidas.cantidad_maxima_tiradas, count(*) as cantidad_tiradas " +
							"from mastermind.partidas join mastermind.tiradas on partidas.id_partida = tiradas.id_partida " +
							"where partidas.partida_acabada = 0 " +
							"group by partidas.id_partida, partidas.nick, partidas.fecha, partidas.cantidad_maxima_tiradas having count(*)";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		JTable table = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PWD); 
			st = con.createStatement(); 
			rs = st.executeQuery(querySelect);
			
			Vector<Vector> rowData = new Vector<Vector>();
			Vector<Object> columnNames = new Vector<Object>();
			
			columnNames.add("Nick");
			columnNames.add("Fecha");
			columnNames.add("Tipo partida");
			columnNames.add("Numero de tiradas");
			
			while (rs.next()) {
				Vector<String> row = new Vector<String>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				if (rs.getString(3).equals("0")) row.add("Principiante"); else row.add("Dificil");
				row.add(rs.getString(4));
				rowData.add(row);
			}
			
			table = new JTable(rowData, columnNames);
			
			if (st != null) st.close();
			if (con != null) con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			Joc.mostraSQLException(e);
		} finally {}
		
		return table;
	}

	abstract class NuevaPartidaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			partida.crearNumeroAleatorio();
			Component[] componentList = panelTiradas.getComponents();
			for (Component c: componentList) if (c instanceof JScrollPane) panelTiradas.remove(c);
			
			scrollTableTiradas = new JScrollPane(tableTiradas);
			panelTiradas.add(scrollTableTiradas, BorderLayout.CENTER);
		}
	}
	class NuevaPartidaPrincipianteListener extends NuevaPartidaListener {
		public void actionPerformed(ActionEvent e) {
			partida = new PartidaPrincipiante();
			labelTitulo.setText("NUEVA PARTIDA PRINCIPIANTE");
			tableTiradas = new JTable();
			dmTableTiradas = new DefaultTableModel();
			dmTableTiradas.addColumn("Entrada"); dmTableTiradas.addColumn("Bien pos."); dmTableTiradas.addColumn("Mal pos.");
			tableTiradas.setModel(dmTableTiradas);
			super.actionPerformed(e);
		}
	}
	class NuevaPartidaAvanzadaListener extends NuevaPartidaListener  {
		public void actionPerformed(ActionEvent e) {
			partida = new PartidaAvanzada();
			labelTitulo.setText("NUEVA PARTIDA AVANZADA");
			tableTiradas = new JTable();
			dmTableTiradas = new DefaultTableModel();
			dmTableTiradas.addColumn("Entrada"); dmTableTiradas.addColumn("Bien pos."); dmTableTiradas.addColumn("Mal pos."); dmTableTiradas.addColumn("Vidas"); 
			tableTiradas.setModel(dmTableTiradas);
			super.actionPerformed(e);
		}
	}
	class GuardarPartidaListener implements ActionListener {
		public void actionPerformed(ActionEvent ex) {
			try {
				GuardarPartida.getInstance().actua(getPartida());
			} catch (Exception e) {
				System.out.println("ERROR No se ha podido guardar la partida.");
			} finally {
				System.out.println("\nLa partida se ha guardado con exito.\n");
			}
			
		}
	}
	/*
	class CargarPartidaListener implements ActionListener {
		public void actionPerformed(ActionEvent ex) {
			CargarPartida.getInstance().actua();
			
		}
	}
	*/
	class EnviarEntradaListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			
			String entrada = textFieldEntrada.getText();
			
			try {
				Integer.parseInt(entrada);
				
				byte[] arrayEntrada = Joc.numeroStringToByteArray(entrada);
				byte[] arrayNumeroAleatorio = partida.getNumeroAleatorio();
				
				Tirada tirada = new Tirada(arrayEntrada, arrayNumeroAleatorio);
				
				tirada.crearBienPosicionados();
				tirada.crearMalPosicionados();
				partida.addTirada(tirada);
				partida.setPartidaAcabada();
				
				if (partida instanceof PartidaPrincipiante) {
					dmTableTiradas.addRow(new Object[]{tirada.getEntradaString(), tirada.getBienPosicionadosString(), tirada.getMalPosicionadosString()});
				}
				if (partida instanceof PartidaAvanzada) {
					PartidaAvanzada partidaAvanzada = (PartidaAvanzada)partida;
					dmTableTiradas.addRow(new Object[]{tirada.getEntradaString(), tirada.getCantidadBienPosicionados(), tirada.getCantidadMalPosicionados(), partidaAvanzada.getCantidadVidas()});
				}
				if (partida.getPartidaAcabada()) System.out.println("\nPARTIDA ACABADA");
				
			} catch (NumberFormatException e) {}
		}
	}
	
}

class GuardarPartida {
	String URL = "jdbc:mysql://localhost:3307/mastermind", USER = "root", PWD = "";
		
	static GuardarPartida INSTANCE = null;
	
	GuardarPartida(){}
	
	public static GuardarPartida getInstance() {
		if (INSTANCE == null) GuardarPartida.INSTANCE = new GuardarPartida();
		return GuardarPartida.INSTANCE;
	}
	
	String getUrl() {return this.URL;}
	String getUser() {return this.USER;}
	String getPwd() {return this.PWD;}
	
	
	public void actua(Partida partida) throws Exception {
		Connection con = null; Statement st = null; ResultSet rs = null;
		
		System.out.println (partida.getFecha());
		
		int pa;
		if (partida.getPartidaAcabada()) pa = 1;
		else pa = 0;
		
		String insertPartida = 	"insert into partidas (numero_aleatorio, nick, fecha, cantidad_maxima_tiradas, partida_acabada) " + 
								"values( '" + partida.getNumeroAleatorioString() + "', '" + partida.getNick() + "', '" + partida.getFecha() + "', " + partida.getVidas() + ", " + pa + ")";
		String insertTirada;
		
		
		String selectUltimaPartida =	"select max(partidas.id_partida) as id_max from partidas";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(getUrl(), getUser(), getPwd());
			st = con.createStatement();
			st.executeUpdate(insertPartida);
			rs = st.executeQuery(selectUltimaPartida);
			rs.next();
			partida.setId(rs.getInt("id_max"));
			
			for (Iterator it = partida.getTiradas().iterator(); it.hasNext();) {
				Tirada tirada = (Tirada)it.next();
				insertTirada =	"insert into tiradas (id_partida, numero_entrado, mal_posicionados, bien_posicionados)" +
								"values(" + partida.getId() + ", '" + tirada.getEntradaString() + "', '" + tirada.getMalPosicionadosString() + "', '" + tirada.getBienPosicionadosString() + "')";
				st.executeUpdate(insertTirada);
			}
				
			if (st != null) st.close();
			if (con != null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			Joc.mostraSQLException(e);
		} finally {
			
		} 
	}
	
}

class CargarPartida {
	String URL = "jdbc:mysql://localhost:3307/mastermind", USER = "root", PWD = "";
	String querySelect =	"select partidas.nick, partidas.fecha, partidas.cantidad_maxima_tiradas, count(*) as cantidad_tiradas " +
							"from mastermind.partidas join mastermind.tiradas on partidas.id_partida = tiradas.id_partida " +
							"where partidas.partida_acabada = 0 " +
							"group by partidas.id_partida, partidas.nick, partidas.fecha, partidas.cantidad_maxima_tiradas having count(*)";
		
	static CargarPartida INSTANCE = null;
	
	JFrame frame = new JFrame("Cargar partida");
	JPanel panel;
	JTable table;
	
	CargarPartida(){}
	
	public void actua() {
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		panel = (JPanel)frame.getContentPane();
		panel.setLayout(new BorderLayout());
		
		
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PWD); 
			st = con.createStatement(); 
			rs = st.executeQuery(querySelect);
			
			Vector<Vector> rowData = new Vector<Vector>();
			Vector<Object> columnNames = new Vector<Object>();
			
			columnNames.add("Numero aleatorio");
			columnNames.add("Nick");
			columnNames.add("Fecha");
			columnNames.add("Numero de vidas");
			columnNames.add("Partida acabada?");
			
			while (rs.next()) {
				Vector<String> row = new Vector<String>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				rowData.add(row);
			}
			
			table = new JTable(rowData, columnNames);
			JScrollPane scroll = new JScrollPane(table);
			panel.add(scroll, BorderLayout.CENTER);	
			
			if (st != null) st.close();
			if (con != null) con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			Joc.mostraSQLException(e);
		} finally {}
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}
	
	public static CargarPartida getInstance() {
		if (INSTANCE == null) CargarPartida.INSTANCE = new CargarPartida();
		return CargarPartida.INSTANCE;
	}
	
	String getUrl() {return this.URL;}
	String getUser() {return this.USER;}
	String getPwd() {return this.PWD;}
	
	
	void mostraSQLException(SQLException ex) {
		ex.printStackTrace(System.err);
		System.err.println("SQLState:   " + ex.getSQLState());
		System.err.println("Error Code: " + ex.getErrorCode());
		System.err.println("Message:    " + ex.getMessage());
		Throwable t;
		while ((t = ex.getCause()) != null) System.out.println("Cause:      " + t);
	}
	public void actua(Partida partida) throws Exception {
		Connection con = null; Statement st = null; ResultSet rs = null;
		
		System.out.println (partida.getFecha());
		
		String insertPartida = 	"insert into partidas (numero_aleatorio, nick, fecha, cantidad_maxima_tiradas) " + 
								"values( '" + partida.getNumeroAleatorioString() + "', '" + partida.getNick() + "', '" + partida.getFecha() + "', " + partida.getVidas() + ")";
		String insertTirada;
		
		
		String selectUltimaPartida =	"select max(partidas.id_partida) as id_max from partidas";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(getUrl(), getUser(), getPwd());
			st = con.createStatement();
			st.executeUpdate(insertPartida);
			rs = st.executeQuery(selectUltimaPartida);
			rs.next();
			partida.setId(rs.getInt("id_max"));
			
			for (Iterator it = partida.getTiradas().iterator(); it.hasNext();) {
				Tirada tirada = (Tirada)it.next();
				insertTirada =	"insert into tiradas (id_partida, numero_entrado, mal_posicionados, bien_posicionados)" +
								"values(" + partida.getId() + ", '" + tirada.getEntradaString() + "', '" + tirada.getMalPosicionadosString() + "', '" + tirada.getBienPosicionadosString() + "')";
				st.executeUpdate(insertTirada);
			}
				
			if (st != null) st.close();
			if (con != null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			mostraSQLException(e);
		} finally {
			
		} 
	}
	
}

public class Joc {
	static Scanner in = new Scanner(System.in);
	JocFrame frame = new JocFrame();
	
	public static void nuevaPartida(Partida partida) {
			
		partida.crearNumeroAleatorio();
		
		while (!partida.getPartidaAcabada()) {
			Tirada tirada = new Tirada(obtenerNumeroEntradoEnConsola(), partida.getNumeroAleatorio());
			
			tirada.crearBienPosicionados();
			tirada.crearMalPosicionados();
					
			partida.addTirada(tirada);
			
			partida.setPartidaAcabada();
			
			imprimirTiradaEnConsola(partida);
		}
		
		System.out.println("\n" + partida);
	}
	
	public static void mostraSQLException(SQLException ex) {
		ex.printStackTrace(System.err);
		System.err.println("SQLState:   " + ex.getSQLState());
		System.err.println("Error Code: " + ex.getErrorCode());
		System.err.println("Message:    " + ex.getMessage());
		Throwable t;
		while ((t = ex.getCause()) != null) System.out.println("Cause:      " + t);
	}
	
	static boolean escogerDificultad() {
		char opcion;
		
		System.out.println ("Por favor, escoge una dificultad: ");
		System.out.println ("\t1 - Principiante.\n\t Muestra donde esta cada numero bien y mal posicionado. Intentos ilimitados.");
		System.out.println ("\t2 - Avanzado.\n\t Muestra solo la cantidad de numeros bien y mal posicionados. 10 intentos.\n");
		
		if ((opcion = in.next().charAt(0)) == '2') {
			return true;
		} else {
			return false;
		}
		
	}
	
	static byte[] obtenerNumeroEntradoEnConsola() {
		byte[] entrada = new byte[5];
		String numeroEntrado = "";
		boolean numeroCorrecto = false;
		
		System.out.println ("\nPor favor, inserta un numero de entre 1 y 5 cifras: ");
		while (!numeroCorrecto) {
			numeroEntrado = in.next();
			

			if (numeroEntrado.length() < 1 || numeroEntrado.length() > 5) {
				System.out.println ("Numero incorrecto. Por favor, inserta otro.");
			} else {
				numeroCorrecto = true;
			}
		}
		
		StringBuffer sb = new StringBuffer(numeroEntrado);
		
		while (sb.length() < 5) {
			sb.insert(0, '0');
		}
		
		numeroEntrado = sb.toString();
		
		for (int i = 0; i < numeroEntrado.length(); i++) {
			entrada[i] = Byte.parseByte("" + numeroEntrado.charAt(i));
		}
		
		return entrada;
	}
	
	public static byte[] numeroStringToByteArray(String numeroEntrado) {
		byte[] entrada = {0,0,0,0,0};
		boolean numeroCorrecto = false;
		
		try { // comprobar si numeroEntrado es un numero. Si no, retornar entrada sin mas
			Integer.parseInt(numeroEntrado);
		} catch (NumberFormatException e) {
			return entrada;
		}
		
		StringBuffer sb = new StringBuffer(numeroEntrado);
		
		while (sb.length() < 5) {
			sb.insert(0, '0');
		}
		
		numeroEntrado = sb.toString();
		
		for (int i = 0; i < numeroEntrado.length(); i++) {
			entrada[i] = Byte.parseByte("" + numeroEntrado.charAt(i));
		}
		
		return entrada;
	}
	
	static void imprimirTiradaEnConsola(Partida partida) {
		
		if (partida instanceof PartidaBasica) System.out.println(partida.getUltimaTirada());
		if (partida instanceof PartidaAvanzada) {
			
			PartidaAvanzada partidaAvanzada = (PartidaAvanzada)partida;
			
			Tirada ultimaTirada = partida.getUltimaTirada();
			
			System.out.println("\nBien posicionados: " + ultimaTirada.getCantidadBienPosicionados());
			System.out.println("Mal posicionados:  " + ultimaTirada.getCantidadMalPosicionados());
			System.out.println("Vidas:             " + partidaAvanzada.getCantidadVidas());
		}
		
	}
	
	static void intro() {
		System.out.println ("--< MASTERMIND >--\n");
		System.out.println ("REGLAS:");
		System.out.println ("- Has de introducir un numero entre 0 y 99999.");
		System.out.println ("- Si el numero es mas pequeño que 10000 (ex. 2343), se rellenara de ceros\n por la izquierda.");
		System.out.println ("- Si todos los digitos del numero son del mismo valor\ny tienen la misma posicion, GANAS! ");
		System.out.println ("\nGOOD LUCK HAVE FUN!\n");
	}
	
	static String getNick() {
		System.out.println ("Por favor, inserta tu nombre: ");
		return new Scanner(System.in).next();
	}
	
	public static void main (String args[]) {
		new Joc();
	}
}

