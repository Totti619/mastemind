import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.time.*;
import java.nio.file.Files;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.sql.*; // no comments

class Tirada implements Serializable {
	private byte[] entrada, malPosicionados, bienPosicionados, numeroAleatorio;
	private int id, idPartida; 
	
	public Tirada(byte[] entrada, byte[] numeroAleatorio) {
		this.entrada = entrada;
		this.numeroAleatorio = numeroAleatorio;
	}
	public Tirada(int id, int idPartida, String entrada, String mp, String bp) throws NumberFormatException {
		this.id = id; this.idPartida = idPartida;
		setEntrada(entrada);
		setMalPosicionados(mp);
		setBienPosicionados(bp);
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
	public void setEntrada(String arg) throws NumberFormatException {
		byte[] t = new byte[5];
		Integer.parseInt(arg); if (arg.length() == 5) for (int i = 0; i < arg.length(); i++) t[i] = Byte.parseByte("" + arg.charAt(i));
		this.entrada = t;
	}
	public byte[] getMalPosicionados() {
		byte[] t = this.malPosicionados;
		return t;
	}
	public void setMalPosicionados(String arg) throws NumberFormatException {
		byte[] t = new byte[5];
		Integer.parseInt(arg); if (arg.length() == 5) for (int i = 0; i < arg.length(); i++) t[i] = Byte.parseByte("" + arg.charAt(i));
		this.malPosicionados = t;
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
	public void setBienPosicionados(String arg) throws NumberFormatException {
		byte[] t = new byte[5];
		Integer.parseInt(arg); if (arg.length() == 5) for (int i = 0; i < arg.length(); i++) t[i] = Byte.parseByte("" + arg.charAt(i));
		this.bienPosicionados = t;
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
	public PartidaAvanzada(int id, String numeroAleatorio, String nick, String fecha, byte vidas, boolean partidaAcabada, ArrayList<Tirada> tiradas) throws NumberFormatException {
		super(id, numeroAleatorio, nick, fecha, vidas, partidaAcabada, tiradas);
	}
	
	public byte getCantidadVidas() {return this.cantidadVidas;}
	public void setCantidadVidas(byte cantidadVidas) {this.cantidadVidas = cantidadVidas;}
	
	public void setPartidaAcabada() {
		if (!getPartidaAcabada() && !getTiradas().isEmpty()) {
			int bienPosicionados = getUltimaTirada().getCantidadBienPosicionados();
			if (bienPosicionados == 5 || getCantidadVidas() < 2 ||  getCantidadVidas() == 1) setPartidaAcabada(true);
			else setPartidaAcabada(false);
			cantidadVidas--;
		} 
	}
}

class PartidaPrincipiante extends Partida implements Serializable {
	
	public PartidaPrincipiante() {}
	public PartidaPrincipiante(int id, String numeroAleatorio, String nick, String fecha, byte vidas, boolean partidaAcabada, ArrayList<Tirada> tiradas) throws NumberFormatException {
		super(id, numeroAleatorio, nick, fecha, vidas, partidaAcabada, tiradas);
	}
	
	public void setPartidaAcabada() {
		if (!getPartidaAcabada() && !getTiradas().isEmpty()) {
			int bienPosicionados = getUltimaTirada().getCantidadBienPosicionados();
			if (bienPosicionados == 5) setPartidaAcabada(true);
			else setPartidaAcabada(false);
		} 
	}
	
}

abstract class Partida implements Serializable {
	
	private byte[] numeroAleatorio;
	private java.util.List<Tirada> tiradas = new java.util.ArrayList<Tirada>();
	private boolean partidaAcabada = false;
	private String nick = "Anonymous";
	private String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	private byte vidas = 0;
	private int id;
	
	public Partida() {}
	public Partida(int id, String numeroAleatorio, String nick, String fecha, byte vidas, boolean partidaAcabada, ArrayList<Tirada> tiradas) throws NumberFormatException {
		setNumeroAleatorio(numeroAleatorio);
		this.tiradas = new ArrayList<Tirada>(tiradas);
		setPartidaAcabada(partidaAcabada);
		setNick(nick);
		setFecha(fecha);
		setVidas(vidas);
		setId(id);
	}
	
	public byte[] getNumeroAleatorio() {return numeroAleatorio;}
	public void setNumeroAleatorio(String arg) throws NumberFormatException {
		byte[] t = new byte[5];
		Integer.parseInt(arg); if (arg.length() == 5) for (int i = 0; i < arg.length(); i++) t[i] = Byte.parseByte("" + arg.charAt(i));
		this.numeroAleatorio = t;
	}
	public String getNumeroAleatorioString() {
		StringBuffer res = new StringBuffer();
		byte[] t;
		for (int i = 0; i < (t = getNumeroAleatorio()).length; i++) res.append(t[i]);
		return res.toString();
	}
	
	public Tirada getUltimaTirada() {
		if (!getTiradas().isEmpty())
		return getTiradas().get((getTiradas().size()) - 1);
		return null;
	}
	public java.util.List<Tirada> getTiradas() {return new java.util.ArrayList<Tirada>(this.tiradas);}
	
	public boolean getPartidaAcabada() {return this.partidaAcabada;}
	public void setPartidaAcabada(boolean partidaAcabada) {this.partidaAcabada = partidaAcabada;}
	protected abstract void setPartidaAcabada();
	
	public String getNick() {return this.nick;}
	public void setNick(String nick) {this.nick = nick;}
	
	public String getFecha() {return this.fecha;}
	public void setFecha(String fecha) {this.fecha = fecha;}
	
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
		
		sbPartida.append("Jugador: 			  " + getNick() + "\n");
		sbPartida.append("Fecha:   			  " + getFecha() + "\n");
		sbPartida.append("Partida acabada?:   " + getPartidaAcabada() + "\n");
		sbPartida.append("Vidas:              " + getVidas() + "\n");
		sbPartida.append("\t------ INICIO TIRADAS ------\n");
		for (Iterator iterator = tiradas.iterator(); iterator.hasNext();) {
			sbPartida.append(iterator.next().toString() + " #" + contador + "\n");
			contador++;
		}
		sbPartida.append("\t------ FIN TIRADAS ------\n");
		return sbPartida.toString();
	}
}

class AyudaFrame {
	JFrame frame = new JFrame("Sobre MasterMind");
	JPanel panel = (JPanel)frame.getContentPane();
	
	JTextArea textArea = new JTextArea();
	
	JButton buttonOk = new JButton("OK!");
	
	public AyudaFrame() {
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(700, 300);
		
		textArea.setEditable(false);
		
		panel.setLayout(new BorderLayout());
		panel.add(textArea, BorderLayout.CENTER);
		panel.add(buttonOk, BorderLayout.SOUTH);
		
		buttonOk.addActionListener(new OkListener());
		
		escribirAyuda("../ayuda.txt");
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private String getFileContentFromPath(String path) throws FileNotFoundException, IOException {
		StringBuffer file = new StringBuffer();
		int c = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)))); // obtener html desde url
		while((c = br.read()) != -1) file.append((char)c); br.close();// pasar html obtenido a un string caracter por caracter
		return file.toString();
	}
	
	private void escribirAyuda(String path) {
		String contenido = "";
		try {
			contenido = getFileContentFromPath(path);
			textArea.setText(contenido);
		} catch (FileNotFoundException e) {
			System.out.println ("No se ha encontrado el archivo " + path);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	class OkListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			frame.setVisible(false);
			frame.dispose();
		}
	}
}

class JocFrame {
	
	private Vector<Vector> rowData = null;
	private Vector<String> columnNames = null;
	
	private JFrame frame = new JFrame("MasterMind");
	private JPanel panel;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuNuevaPartida = new JMenu("Nueva partida");
	private JMenuItem itemNuevaPartidaPrincipiante = new JMenuItem("Principiante");
	private JMenuItem itemNuevaPartidaAvanzada = new JMenuItem("Avanzada");
	private JLabel itemLabelNick = new JLabel("Nick:");
	private JTextField itemTextFieldNick = new JTextField("Anonymous");
	private JMenuItem itemAyuda = new JMenuItem("Ayuda");
	
	private JLabel labelTitulo = new JLabel("MASTERMIND");
	private JPanel panelTitulo = new JPanel(new BorderLayout());
	
	private JPanel panelTiradas = new JPanel();
	private JTable tableTiradas;
	public DefaultTableModel dmTableTiradas, dmTablePartidas;
	private JScrollPane scrollTableTiradas = new JScrollPane(tableTiradas);
	private JTextField textFieldEntrada = new JTextField();
	private JButton buttonEntrada = new JButton("Entrar");
	private JPanel panelEntrada;
	
	private JPanel panelPartidas;
	private JLabel labelPartidasGuardadas = new JLabel("Partidas Guardadas");
	private JTable tablePartidas = new JTable();
	private ListSelectionModel tablePartidasSelectionModel;
	private JScrollPane scrollTablePartidas;
	private JPanel panelPartidasBotones;
	private JButton buttonBorrarPartida;
	private JButton buttonBorrarTodo;

	private Partida partida;
	private BD bd = BD.getInstance();
	
	public JocFrame() {
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(950, 550);
		
		menuBar.add(menuNuevaPartida);
		menuNuevaPartida.add(itemNuevaPartidaPrincipiante);
		menuNuevaPartida.add(itemNuevaPartidaAvanzada);
		//menuBar.add(new JSeparator());
		menuBar.add(itemLabelNick);
		menuBar.add(itemTextFieldNick);
		menuBar.add(itemAyuda);
		
		panelEntrada = new JPanel();
		panelEntrada.setLayout(new BorderLayout());
		panelEntrada.add(textFieldEntrada, BorderLayout.CENTER);
		panelEntrada.add(buttonEntrada, BorderLayout.EAST);
		
		panelTitulo.add(labelTitulo, BorderLayout.CENTER);
		
		panelTiradas.setLayout(new BorderLayout());
		
		actualizaPanelPartidas();
		
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
		buttonEntrada.addActionListener(new AddTiradaListener());
		itemAyuda.addActionListener(new AyudaListener());
		
		frame.setJMenuBar(menuBar);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private Partida getPartida() {
		return this.partida;
	}
	
	public void actualizaPanelPartidas() {
		Component[] componentList = panelTiradas.getComponents();
		for (Component c: componentList) if (c instanceof Component) panelPartidas.remove(c);
		panelPartidas = new JPanel();
		panelPartidas.setLayout(new BorderLayout());
		dmTablePartidas = bd.getTablaPartidas();
		tablePartidas.setModel(dmTablePartidas);
		tablePartidasSelectionModel = tablePartidas.getSelectionModel();
		tablePartidasSelectionModel.addListSelectionListener(new SelectionHandler());
		tablePartidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollTablePartidas = new JScrollPane(tablePartidas);
		panelPartidas.add(scrollTablePartidas, BorderLayout.CENTER);
		panelPartidasBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonBorrarTodo = new JButton("Borrar todo");
		buttonBorrarTodo.setForeground(Color.white);
		buttonBorrarTodo.setBackground(Color.red);
		buttonBorrarTodo.addActionListener(new BorrarTodoListener());
		panelPartidasBotones.add(buttonBorrarTodo);
		panelPartidas.add(panelPartidasBotones, BorderLayout.SOUTH);
	}
	public void actualizaPanelTiradas() {
		
	}
	
	class BorrarTodoListener implements ActionListener {
		private JFrame frame = new JFrame("Advertencia");
		private JPanel panel = (JPanel)frame.getContentPane();
		private JLabel label = new JLabel("La siguiente accion borrara todas\n las partidas de la BD, al igual que sus tiradas.\n Desea continuar?");
		private JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		private JButton si = new JButton("Si"), no = new JButton("No");
		
		public void actionPerformed(ActionEvent ae) {
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.setSize(600, 200);
			panel.setLayout(new BorderLayout());
			
			panel.add(label, BorderLayout.CENTER);
			panelButtons.add(si);
			panelButtons.add(no);
			panel.add(panelButtons, BorderLayout.SOUTH);
			
			si.addActionListener(new SiListener());
			no.addActionListener(new NoListener());
			
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
		}
		
		abstract class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				frame.setVisible(false);
				actualizaPanelPartidas();
				frame.dispose();
			}
		}
		class SiListener extends ButtonListener {
			public void actionPerformed(ActionEvent ae) {
				bd.borrarTodo();
				super.actionPerformed(ae);
			}
		}
		class NoListener extends ButtonListener {
			public void actionPerformed(ActionEvent ae) {
				super.actionPerformed(ae);
			}
		}
	}

	abstract class NuevaPartidaListener implements ActionListener {
		
		public void addComponents() {
			Component[] componentList = panelTiradas.getComponents();
			for (Component c: componentList) if (c instanceof JComponent) panelTiradas.remove(c);
			scrollTableTiradas = new JScrollPane(tableTiradas);
			panelTiradas.add(scrollTableTiradas, BorderLayout.CENTER);
			panelTiradas.add(panelEntrada, BorderLayout.SOUTH);
			//actualizaPanelPartidas();
		}
		
		public void actua() {
			partida.crearNumeroAleatorio();
			partida.setNick(itemTextFieldNick.getText());
			try {
				bd.guardaPartida(getPartida());
				String tipoPartida = "";
				if (partida instanceof PartidaAvanzada) tipoPartida = "Avanzada";
				if (partida instanceof PartidaPrincipiante) tipoPartida = "Principiante";
				dmTablePartidas.addRow(new Object[]{partida.getId(), partida.getNick(), partida.getFecha(), tipoPartida, partida.getVidas(), "NO"});
			} catch (Exception e) {
				//System.out.println("ERROR No se ha podido guardar la partida.");
				e.printStackTrace();
			} finally {
				System.out.println("\nLa partida se ha guardado con exito.\n");
			}
		}
		public void actionPerformed(ActionEvent ex) {
			actua();
		}
	}
	class NuevaPartidaPrincipianteListener extends NuevaPartidaListener {
		public void cargaPartida(PartidaPrincipiante p) {
			labelTitulo.setText("CONTINUAR PARTIDA PRINCIPIANTE");
			tableTiradas = new JTable();
			dmTableTiradas = new DefaultTableModel();
			tableTiradas.setModel(dmTableTiradas);
			scrollTableTiradas = new JScrollPane(tableTiradas);
			panelTiradas.add(scrollTableTiradas, BorderLayout.CENTER);
			dmTableTiradas.addColumn("Entrada"); dmTableTiradas.addColumn("Bien pos."); dmTableTiradas.addColumn("Mal pos.");
			for(Iterator it = p.getTiradas().iterator(); it.hasNext();) {
				Tirada t = (Tirada)it.next();
				dmTableTiradas.addRow(new Object[]{t.getEntradaString(), t.getBienPosicionadosString(), t.getMalPosicionadosString()});
			}
			partida = p;
			bd.setPartida(partida);
			super.addComponents();
		}
		public void actua(PartidaPrincipiante p) {
			partida = p;
			labelTitulo.setText("NUEVA PARTIDA PRINCIPIANTE");
			tableTiradas = new JTable();
			dmTableTiradas = new DefaultTableModel();
			dmTableTiradas.addColumn("Entrada"); dmTableTiradas.addColumn("Bien pos."); dmTableTiradas.addColumn("Mal pos.");
			tableTiradas.setModel(dmTableTiradas);
			super.actua();
		}
		public void actionPerformed(ActionEvent e) {
			partida = new PartidaPrincipiante();
			labelTitulo.setText("NUEVA PARTIDA PRINCIPIANTE");
			tableTiradas = new JTable();
			dmTableTiradas = new DefaultTableModel();
			dmTableTiradas.addColumn("Entrada"); dmTableTiradas.addColumn("Bien pos."); dmTableTiradas.addColumn("Mal pos.");
			tableTiradas.setModel(dmTableTiradas);
			super.actionPerformed(e);
			super.addComponents();
		}
	}
	class NuevaPartidaAvanzadaListener extends NuevaPartidaListener  {
		public void cargaPartida(PartidaAvanzada p) {
			labelTitulo.setText("CONTINUAR PARTIDA AVANZADA");
			tableTiradas = new JTable();
			dmTableTiradas = new DefaultTableModel();
			tableTiradas.setModel(dmTableTiradas);
			scrollTableTiradas = new JScrollPane(tableTiradas);
			panelTiradas.add(scrollTableTiradas, BorderLayout.CENTER);
			dmTableTiradas.addColumn("Entrada"); dmTableTiradas.addColumn("Bien pos."); dmTableTiradas.addColumn("Mal pos."); dmTableTiradas.addColumn("Vidas");
			for(Iterator it = p.getTiradas().iterator(); it.hasNext();) {
				Tirada t = (Tirada)it.next();
				dmTableTiradas.addRow(new Object[]{t.getEntradaString(), t.getCantidadBienPosicionados(), t.getCantidadMalPosicionados(), ((PartidaAvanzada)p).getCantidadVidas()});
			}
			partida = p;
			bd.setPartida(partida);
			super.addComponents();
		}
		public void actua(PartidaAvanzada p) {
			partida = p;
			labelTitulo.setText("NUEVA PARTIDA AVANZADA");
			tableTiradas = new JTable();
			dmTableTiradas = new DefaultTableModel();
			dmTableTiradas.addColumn("Entrada"); dmTableTiradas.addColumn("Bien pos."); dmTableTiradas.addColumn("Mal pos."); dmTableTiradas.addColumn("Vidas"); 
			tableTiradas.setModel(dmTableTiradas);
			super.actua();
		}
		public void actionPerformed(ActionEvent e) {
			partida = new PartidaAvanzada();
			labelTitulo.setText("NUEVA PARTIDA AVANZADA");
			tableTiradas = new JTable();
			dmTableTiradas = new DefaultTableModel();
			dmTableTiradas.addColumn("Entrada"); dmTableTiradas.addColumn("Bien pos."); dmTableTiradas.addColumn("Mal pos."); dmTableTiradas.addColumn("Vidas"); 
			tableTiradas.setModel(dmTableTiradas);
			super.actionPerformed(e);
			super.addComponents();
		}
	}

	class AddTiradaListener implements ActionListener {
		
		private byte[] numeroStringToByteArray(String entrada) throws NumberFormatException {
			byte[] t = new byte[5];
			if (entrada.length() < 1 || entrada.length() > 5) throw new NumberFormatException();
			Integer.parseInt(entrada); // comprueba que sea numerico, en caso contrario lanzara NumberFormatException
			while (entrada.length() < 5) entrada = "0" + entrada;
			for (int i = 0; i < entrada.length(); i++) t[i] = Byte.parseByte("" + entrada.charAt(i));
			return t;
		}
		
		public void actionPerformed(ActionEvent ae) {
			bd.setPartida(partida);
			System.out.println (partida.getPartidaAcabada());
			
			
			if (!partida.getPartidaAcabada()) {
				String entrada = textFieldEntrada.getText();
			
				try {
					
					byte[] arrayEntrada = numeroStringToByteArray(entrada);
					byte[] arrayNumeroAleatorio = partida.getNumeroAleatorio();
					
					Tirada tirada = new Tirada(arrayEntrada, arrayNumeroAleatorio);
					
					tirada.crearBienPosicionados();
					tirada.crearMalPosicionados();
					partida.addTirada(tirada);
					partida.setPartidaAcabada();
					bd.guardaTirada(tirada);
					
					if (partida instanceof PartidaPrincipiante) {
						dmTableTiradas.addRow(new Object[]{tirada.getEntradaString(), tirada.getBienPosicionadosString(), tirada.getMalPosicionadosString()});
					}
					if (partida instanceof PartidaAvanzada) {
						PartidaAvanzada partidaAvanzada = (PartidaAvanzada)partida;
						dmTableTiradas.addRow(new Object[]{tirada.getEntradaString(), tirada.getCantidadBienPosicionados(), tirada.getCantidadMalPosicionados(), partidaAvanzada.getCantidadVidas()});
					}
					if (getPartida().getPartidaAcabada()) System.out.println("\nPARTIDA ACABADA");
					
				} catch (NumberFormatException e) {
					System.out.println ("El valor no es correcto.");
				}
			} else System.out.println ("No se puede crear mas tiradas. La partida ya esta acabada.");
		}
	}
	
	class SelectionHandler implements ListSelectionListener {
		private String id;
		
		private String getId() {return id;}
		private void setId(String id) {this.id = id;}
		
		public void valueChanged(ListSelectionEvent e) {
			if (! e.getValueIsAdjusting()) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				
				String s = "";
				int index = 0;
				int firstIndex = e.getFirstIndex();
				int lastIndex = e.getLastIndex();
				boolean isAdjusting = e.getValueIsAdjusting();
				s = "Event for indexes "
							  + firstIndex + " - " + lastIndex
							  + "; isAdjusting is " + isAdjusting
							  + "; selected indexes:";

				if (lsm.isSelectionEmpty()) {
					s = s + " <none>";
				} else {
					// Find out which indexes are selected.
					int minIndex = lsm.getMinSelectionIndex();
					int maxIndex = lsm.getMaxSelectionIndex();
					for (int i = minIndex; i <= maxIndex; i++) {
						if (lsm.isSelectedIndex(i)) {
							index = i;
						}
					}
				}
			
				setId((String)dmTablePartidas.getValueAt(index, 0));
				Partida partida = bd.cargarPartida(getId());
				Component[] componentList = panelTiradas.getComponents();
				tableTiradas = new JTable();
				scrollTableTiradas = new JScrollPane();
				dmTableTiradas = new DefaultTableModel();
				for (Component c: componentList) if (c instanceof JScrollPane) panelTiradas.remove(c);
				for (Component c: componentList) if (c instanceof JTable) panelTiradas.remove(c);
				if (partida instanceof PartidaPrincipiante) new NuevaPartidaPrincipianteListener().cargaPartida((PartidaPrincipiante)partida);
				if (partida instanceof PartidaAvanzada) new NuevaPartidaAvanzadaListener().cargaPartida((PartidaAvanzada)partida);
				
				
			}
		}
	}
	class AyudaListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			new AyudaFrame();
		}
	}
	
}

class BD {
	private final String DRIVER = "com.mysql.jdbc.Driver", URL = "jdbc:mysql://localhost:3307/mastermind", USER = "root", PWD = "";
		
	private static BD INSTANCE = null;
	
	private Partida partida = null;
	
	private BD(){}
	
	public static BD getInstance() {
		if (INSTANCE == null) BD.INSTANCE = new BD();
		return INSTANCE;
	}
	
	private String getDriver() {return this.DRIVER;}
	private String getUrl() {return this.URL;}
	private String getUser() {return this.USER;}
	private String getPwd() {return this.PWD;}
	private Partida getPartida() {return this.partida;}
	public void setPartida(Partida partida) {this.partida = partida;}
	
	public static void mostraSQLException(SQLException ex) {
		ex.printStackTrace(System.err);
		System.err.println("SQLState:   " + ex.getSQLState());
		System.err.println("Error Code: " + ex.getErrorCode());
		System.err.println("Message:    " + ex.getMessage());
		Throwable t;
		while ((t = ex.getCause()) != null) System.out.println("Cause:      " + t);
	}
	
	private Connection conectaBD(String driver, String url, String user, String pwd) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection(url, user, pwd);
	}
	
	public void guardaPartida(Partida partida) throws Exception {
		setPartida(partida);
		
		boolean partidaExiste = false;
		
		Connection con = null; Statement st = null; ResultSet rs = null;
		
		int pa = 0;
		if (partida.getPartidaAcabada()) pa = 1;
		
		String insertPartida = 	"insert into partidas (numero_aleatorio, nick, fecha, cantidad_maxima_tiradas, partida_acabada) " + 
								"values( '" + partida.getNumeroAleatorioString() + "', '" + partida.getNick() + "', '" + partida.getFecha() + "', " + partida.getVidas() + ", " + pa + ")";
		
		String selectUltimaPartida =	"select max(partidas.id_partida) as id_max from partidas";
		
		try {
	
			st = (con = conectaBD(getDriver(), getUrl(), getUser(), getPwd())).createStatement();
			st.executeUpdate(insertPartida);
			rs = st.executeQuery(selectUltimaPartida);
			rs.next(); partida.setId(rs.getInt("id_max"));
				
			if (st != null) st.close();
			if (con != null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			mostraSQLException(e);
		} finally {
			
		} 
	}
	
	public void borrarTodo() {
		String deletePartidas = "delete from partidas", deleteTiradas = "delete from tiradas";
		Connection con = null; Statement st = null;
		try {
			st = (con = conectaBD(getDriver(), getUrl(), getUser(), getPwd())).createStatement();
			st.executeUpdate(deleteTiradas);
			st.executeUpdate(deletePartidas);
				
			if (st != null) st.close();
			if (con != null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			mostraSQLException(e);
		} finally {
			
		}
	}
	
	public Partida cargarPartida(String id) {
		Partida partida = null;
		ArrayList<Tirada> tiradas = new ArrayList<Tirada>();
		
		
		String selectPartida =	"select * from mastermind.partidas where partidas.id_partida = " + id;
		String selectTiradas =	"select * from mastermind.tiradas where tiradas.id_partida = " + id;
		String selectCantTiradas = 	"select count(*) from mastermind.tiradas where tiradas.id_partida = " + id;
		
		
		Connection con = null; Statement st = null; ResultSet rs = null;
		try {
	
			st = (con = conectaBD(getDriver(), getUrl(), getUser(), getPwd())).createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(selectTiradas);
			rs.first();
			while (rs.next()) {
				Tirada t = new Tirada(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5));
				tiradas.add(t);
			}
				
			rs = st.executeQuery(selectPartida);
			rs.next(); 
			if (rs.getInt(5) == 0) {
				partida = new PartidaPrincipiante(rs.getInt(1),  rs.getString(2), rs.getString(3), rs.getString(4), rs.getByte(5), rs.getBoolean(6), tiradas);
				
				
				
			} else {
				int vidas = rs.getInt(5), cantTiradas = 0;
				partida = new PartidaAvanzada(rs.getInt(1),  rs.getString(2), rs.getString(3), rs.getString(4), rs.getByte(5), rs.getBoolean(6), tiradas);
				rs = st.executeQuery(selectCantTiradas); rs.next();
				cantTiradas = rs.getInt(1);
				((PartidaAvanzada)partida).setCantidadVidas((byte)(vidas - cantTiradas));
			}
				
			System.out.println (partida);	
				
			if (st != null) st.close(); if (con != null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			mostraSQLException(e);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			
		} 
		return partida;
	}
	
	public void guardaTirada(Tirada tirada) {
		String insertTirada = 	"insert into tiradas (id_partida, numero_entrado, mal_posicionados, bien_posicionados) values" +
								"(" + partida.getId() + ", '" + tirada.getEntradaString() + "', '" + tirada.getMalPosicionadosString() + "', '" + tirada.getBienPosicionadosString() + "')";
		String updatePartida = 	"update mastermind.partidas set partida_acabada = " + partida.getPartidaAcabada() + " where partidas.id_partida = " + partida.getId();
		Connection con = null; Statement st = null;						
		try {
			st = (con = conectaBD(getDriver(), getUrl(), getUser(), getPwd())).createStatement();
			st.executeUpdate(insertTirada);
			st.executeUpdate(updatePartida);
			
			if (st != null) st.close();
			if (con != null) con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			mostraSQLException(e);
		} finally {
			
		}
	}
	
	public DefaultTableModel getTablaPartidas() {
		String querySelect ="select partidas.id_partida, partidas.nick, partidas.fecha, partidas.cantidad_maxima_tiradas, partidas.partida_acabada " +
							"from mastermind.partidas ";
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		DefaultTableModel table = null;
		try {
			
			st = (con = conectaBD(getDriver(), getUrl(), getUser(), getPwd())).createStatement();
			rs = st.executeQuery(querySelect);
			
			Vector<Vector> rowData = new Vector<Vector>();
			Vector<Object> columnNames = new Vector<Object>();
			
			columnNames.add("#");
			columnNames.add("Nick");
			columnNames.add("Fecha");
			columnNames.add("Tipo partida");
			columnNames.add("Cant. maxima vidas");
			columnNames.add("Partida acabada?");
			
			while (rs.next()) {
				Vector<String> row = new Vector<String>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				if (rs.getString(4).equals("0")) row.add("Principiante"); else row.add("Avanzada");
				if (rs.getString(4).equals("0")) row.add("N/A"); else row.add(rs.getString(4));
				if (rs.getString(5).equals("0")) row.add("NO"); else row.add("SI");
				rowData.add(row);
			}
			
			
			table = new DefaultTableModel(rowData, columnNames);
			
			if (st != null) st.close();
			if (con != null) con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			mostraSQLException(e);
		} finally {}
		
		return table;
	}
	
}

public class Joc {
	private JocFrame frame = new JocFrame();
	
	public static void main (String args[]) {
		new Joc();
	}
}

