import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Time;
import java.time.Clock;
import java.util.Random;

import javax.print.attribute.EnumSyntax;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Tablero extends JPanel implements Runnable, MouseListener {
	private int puntaje;
	private Image fondo;
	private Joya elemento[][];
	private String usuario,tiempo;
	private int seleccion1X,seleccion2X,seleccion1Y,seleccion2Y;
	private boolean haySeleccion1,haySeleccion2,iniciando;

	public Tablero(String usuario) {
		super();
		this.iniciando=true;
		this.usuario=usuario;
		this.haySeleccion1=false;
		this.haySeleccion2=false;
		Thread hilo=new Thread(this);
		this.fondo=new ImageIcon("tablero2.png").getImage();
		this.setPuntaje(0);
		this.setPreferredSize(new Dimension(this.fondo.getWidth(this),this.fondo.getHeight(this)));repaint();
		elemento=new Joya[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				elemento[i][j]=nuevoElemento();
			}
		}
		Thread contadorSegundos=new Thread(new Runnable() {
			public void run() {
				int h,min,sec;
				sec=0;
				min=0;
				h=0;
				for(;;sec++){
					if(sec==60) {
						sec=0;
						min++;
					}
					if(min==60) {
						min=0;
						h++;
					}
					if(h==24) {
						break;
					}
					Tablero.this.tiempo="";
					if(h<10) {
						tiempo+="0";
					}
					tiempo+=h+":";
					if(min<10) {
						tiempo+="0";
					}
					tiempo+=min+":";
					if(sec<10) {
						tiempo+="0";
					}
					tiempo+=sec;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();
				}
			}
		});
		contadorSegundos.start();
		hilo.start();
		this.addMouseListener(this);
	}
	public Joya nuevoElemento() {
		Random random=new Random();
		return new Joya(random.nextInt(7));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.fondo, 0, 0, this.getWidth(), this.getHeight(),this);
		g.setColor(Color.RED);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 29));
		g.drawString(""+this.puntaje, 531-16*(Integer.toString(this.puntaje).length()),78);
		g.setColor(Color.GREEN);
		g.drawString(usuario, 15, 132);
		g.drawString(tiempo, 650, 132);
		for(int i=0;i<elemento.length;i++) {
			for(int j=0;j<elemento[i].length;j++) {
				g.drawImage(elemento[i][j].dibujaJoya(g), 227+i*40, 130+j*40, 40, 40, this);
			}
		}
		if(haySeleccion1) {
			g.setColor(Color.ORANGE);
			g.drawRect(227+40*seleccion1X, 130+40*seleccion1Y, 40, 40);
		}
		if(haySeleccion2) {
			g.setColor(Color.RED);
			g.drawRect(227+40*seleccion2X, 130+40*seleccion2Y, 40, 40);
		}
	}
	public void cambiarPosiciones(int x,int y,int x2,int y2) {
		//8*8
		//Incluir contador de tiempo
		//
		Joya contenedor;
		contenedor=this.elemento[x][y];
		this.elemento[x][y]=this.elemento[x2][y2];
		this.elemento[x2][y2]=contenedor;
		this.haySeleccion1=false;
		this.haySeleccion2=false;
		this.repaint();
		
	}
	public void eliminarFila(int numFila,int inicio,int largo) {
		if(!iniciando) {
			setPuntaje(largo);
		}
		for(int i=0;i<largo;i++) {
			for(int j=numFila;j>=0;j--) {
				try {
					this.elemento[inicio+i][j]=this.elemento[inicio+i][j-1];
				}
				catch(ArrayIndexOutOfBoundsException e) {
					this.elemento[inicio+i][j]=nuevoElemento();
				}
			}
		}
	}
	public void eliminarColumna(int numColumna,int inicio,int alto) {
		System.out.println("Columna "+numColumna+","+inicio+"  "+alto);
		Joya nuevas[]=new Joya[alto];
		if(!iniciando) {
			setPuntaje(alto);
		}
		if(inicio>2)
			for(int i=0,j=alto;i<=alto;++i,--j) {
				System.out.println(i+","+alto);
				this.elemento[numColumna][inicio+j]=this.elemento[numColumna][inicio-i];
				try {
					this.elemento[numColumna][inicio-i]=this.elemento[numColumna][inicio-i-alto];
				}
				catch(ArrayIndexOutOfBoundsException e) {
					this.elemento[numColumna][inicio-i]=nuevoElemento();
				}
			}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.repaint();
	}
	
	public int getPuntaje(Joya gema1,Joya gema2) {
		return puntaje;
	}
	public void setPuntaje(int puntos) {
		this.puntaje+=puntos*100;
	}
	@Override
	public void run() {
		while(true) {
			for(int i=0;i<elemento.length;i++) {
				int color=elemento[i][0].getColor();
				int columna=i,inicio=0,largo=0;
				for(int j=1;j<elemento[i].length;j++) {
					if(color==elemento[i][j].getColor()) {
						if(largo==0) {
							inicio=j-1;
							largo=2;
						}
						else {
							largo++;
						}
					}
					else {
						if(largo>=3) {
							eliminarColumna(columna,inicio,largo);
						}
						color=elemento[i][j].getColor();
						largo=0;
					}
					if(j==7 && largo>=3) {
						eliminarColumna(columna, inicio, largo);
					}
				}
				try {
					Thread.sleep(45);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String c[]= {
					"amarillo","azul","morado","naranja","plata","rojo","verde"
			};
			for(int j=0;j<elemento.length;j++) {
				int color=elemento[0][j].getColor();
				int fila=j,inicio=0,largo=0;
				for(int i=1;i<elemento[j].length;i++) {
					
					if(color==elemento[i][j].getColor()) {
						if(largo==0) {
							inicio=i-1;
							largo=2;
						}
						else {
							largo++;
						}
					}
					else {
						if(largo>=3) {
							eliminarFila(fila,inicio,largo);
						}
						color=elemento[i][j].getColor();
						largo=0;
					}
					if(i==7 && largo>=3) {
						eliminarFila(fila, inicio, largo);
					}
				}
				try {
					Thread.sleep(45);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.iniciando=false;
		if(arg0.getX()>222 && arg0.getX()<541 && arg0.getY()<445 && arg0.getY()>126) {
			int x=arg0.getX(),y=arg0.getY();
			if(x<267) {
				x=0;
			}
			else {
				if(x<307) {
					x=1;
				}
				else {
					if(x<347) {
						x=2;
					}
					else {
						if(x<387) {
							x=3;
						}
						else {
							if(x<427) {
								x=4;
							}
							else {
								if(x<467) {
									x=5;
								}
								else {
									if(x<507) {
										x=6;
									}
									else {
										x=7;
									}
								}
							}
						}
					}
				}
			}
			if(y<170) {
				y=0;
			}
			else {
				if(y<210) {
					y=1;
				}
				else {
					if(y<250) {
						y=2;
					}
					else {
						if(y<290) {
							y=3;
						}
						else {
							if(y<330) {
								y=4;
							}
							else {
								if(y<370) {
									y=5;
								}
								else {
									if(y<410) {
										y=6;
									}
									else {
										y=7;
									}
								}
							}
						}
					}
				}
			}
			if(this.haySeleccion1) {
				if((Math.abs(this.seleccion1X-x)==1 && Math.abs(this.seleccion1Y-y)==0) || (Math.abs(this.seleccion1X-x)==0 && Math.abs(this.seleccion1Y-y)==1)) {
					this.haySeleccion2=true;
					this.seleccion2X=x;
					this.seleccion2Y=y;
					this.cambiarPosiciones(this.seleccion1X,this.seleccion1Y,this.seleccion2X,this.seleccion2Y);
				}
				else {
					this.haySeleccion1=false;
					this.haySeleccion2=false;
				}
			}
			else{
				this.haySeleccion1=true;
				this.seleccion1X=x;
				this.seleccion1Y=y;
			}
			System.out.println(elemento[x][y].getColor());
			this.repaint();
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
