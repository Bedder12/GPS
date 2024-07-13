import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapPanel extends JPanel {
	private final double SCALE = 0.9;
	private IGraph model;
	private double rotation = 0.0;
	private double x_min_data, x_max_data, y_min_data, y_max_data;
	private double x_mid_data, y_mid_data, x_scale, y_scale;
	private Stroke bs1 = new BasicStroke(1);
	private Stroke bs4 = new BasicStroke(3);

	public MapPanel(IGraph model) {
		super(true);
		this.model = model;
		this.setBackground(Color.WHITE);
		setFocusable(true);
		requestFocus();
		addListeners();
	}

	private void findLimits() {
		boolean first = true;
		for (Vertex v : model.getVertices()) {
			double x = v.getLongitude();
			double y = v.getLatitude();
			if (first) {
				x_min_data = x_max_data = x;
				y_min_data = y_max_data = y;
				first = false;
			} else {
				if (x < x_min_data)
					x_min_data = x;
				if (x > x_max_data)
					x_max_data = x;
				if (y < y_min_data)
					y_min_data = y;
				if (y > y_max_data)
					y_max_data = y;
			}
		}
	}

	public void addListeners() {
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_C: // clear
					model.clear();
					break;
				case KeyEvent.VK_LEFT: // rot left
					rotation -= 0.05;
					break;
				case KeyEvent.VK_RIGHT: // rot right
					rotation += 0.05;
					break;
				}
				repaint();
			}

			public void keyReleased(KeyEvent e) {
				repaint();
			}
		});

		MouseAdapter ma = new MouseAdapter() {
			private double x3, y3;

			private void handle(MouseEvent e, boolean dragged) {
				if (!dragged) // find startVertex
				{
					x3 = e.getX();
					y3 = e.getY();
					Vertex v = findVertex(x3, y3);
					if (v != null) {
						Vertex sv = model.getStartVertex();
						if (sv != null && v != sv)
							model.setTargetVertex(v);
						else
							model.findShortestPath(v);
					} else
						model.clear();
				} else // Rotation by drag
				{
					double x2 = getWidth() / 2.0;
					double y2 = getHeight() / 2.0;
					double x1 = e.getX();
					double y1 = e.getY();
					double dotp = (x1 - x2) * (x3 - x2) + (y1 - y2) * (y3 - y2);
					double mag = Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
							* ((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2)));
					if (mag == 0)
						return;
					double cosv = dotp / mag;
					double v = Math.acos(cosv);// *180/Math.PI;
					double crossp = (x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2);
					if (crossp <= 0)
						rotation += v;
					else
						rotation -= v;
					x3 = x1;
					y3 = y1;
				}
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				handle(e, false);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				handle(e, true);
			}
		};
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		var g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		findLimits();
		double data_width = x_max_data - x_min_data;
		double data_height = y_max_data - y_min_data;
		x_mid_data = x_min_data + data_width / 2;
		y_mid_data = y_min_data + data_height / 2;
		x_scale = width / data_width;
		y_scale = height / data_height;
		if (model.getStartVertex() != null) {
			if (model.getTargetVertex() != null) {
				Vertex tv = model.getTargetVertex();
				printShortestPath(g2d, tv, 5, 15, 15);
			} else
				g2d.drawString(model.getStartVertex().toString(), 5, 15);
		}
		Color color = g2d.getColor();
		g2d.setColor(color);
		g2d.drawRect(0, 0, width, height);
		g2d.translate(width / 2, height / 2);
		g2d.rotate(rotation);
		for (Vertex v : model.getVertices()) {
			double x = v.getLongitude();
			double y = v.getLatitude();
			int pixel_x = (int) (0.5 + SCALE * x_scale * (x - x_mid_data));
			int pixel_y = (int) (0.5 - SCALE * y_scale * (y - y_mid_data));
			g2d.setColor(Color.black);
			g2d.setStroke(bs1);
			int radius = (v.equals(model.getStartVertex())) ? 12 : 4;
			g2d.fillOval(pixel_x - radius / 2, pixel_y - radius / 2, radius, radius);
			g2d.drawOval(pixel_x - radius / 2, pixel_y - radius / 2, radius, radius);
			g2d.setStroke(bs1);
			g2d.setColor(Color.lightGray);
			for (Edge e : v.getEdges()) {
				Vertex v2 = e.getFromVertex();
				double x2 = v2.getLongitude();
				double y2 = v2.getLatitude();
				int pixel_x2 = (int) (0.5 + SCALE * x_scale * (x2 - x_mid_data));
				int pixel_y2 = (int) (0.5 - SCALE * y_scale * (y2 - y_mid_data));
				g2d.drawLine(pixel_x, pixel_y, pixel_x2, pixel_y2);
			}
		}
		g2d.setStroke(bs4);
		// if start and target selected - only draw that path
		if (model.getTargetVertex() != null && model.getTargetVertex() != null) {
			drawThisPath(g2d, model.getTargetVertex());
		} else // otherwise - draw from all to start
		{
			for (Vertex v : model.getVertices()) // spanning tree
			{
				drawThisPath(g2d, v);
			}
		}
	}

	private void drawThisPath(Graphics2D g2d, Vertex target) {
		for (Vertex v2 = target; v2 != null && v2 != model.getStartVertex(); v2 = v2.getPredecessor()) {
			double x = v2.getLongitude();
			double y = v2.getLatitude();
			int pixel_x = (int) (0.5 + SCALE * x_scale * (x - x_mid_data));
			int pixel_y = (int) (0.5 - SCALE * y_scale * (y - y_mid_data));
			Vertex pv = v2.getPredecessor();
			g2d.setColor(Color.blue);
			if (pv != null) {
				double x2 = pv.getLongitude();
				double y2 = pv.getLatitude();
				int pixel_x2 = (int) (0.5 + SCALE * x_scale * (x2 - x_mid_data));
				int pixel_y2 = (int) (0.5 - SCALE * y_scale * (y2 - y_mid_data));
				g2d.drawLine(pixel_x, pixel_y, pixel_x2, pixel_y2);
			}
		}
	}

	private void printShortestPath(Graphics2D g2d, Vertex target, int x, int y, int yoffs) {
		while (target != null) {
			String out = String.format("%4.0f km %s", target.getDistance() / 1000.0, target.toString());
			g2d.drawString(out, x, y);
			y += yoffs;
			target = target.getPredecessor();
		}
	}

	private Vertex findVertex(double mx, double my) {
		Vertex result = null;
		int width = getWidth();
		int height = getHeight();
		findLimits();
		double data_width = x_max_data - x_min_data;
		double data_height = y_max_data - y_min_data;
		double x_mid_data = x_min_data + data_width / 2;
		double y_mid_data = y_min_data + data_height / 2;
		double x_scale = width / data_width;
		double y_scale = height / data_height;
		// g2d.translate(width/2, height/2);
		mx -= width / 2;
		my -= height / 2;
		// rotera runt mittpunkten
		double mxt = mx * Math.cos(-rotation) - my * Math.sin(-rotation);
		my = mx * Math.sin(-rotation) + my * Math.cos(-rotation);
		mx = mxt;

		for (Vertex v : model.getVertices()) {
			double vx = v.getLongitude();
			double vy = v.getLatitude();

			double tx = x_mid_data + mx / (SCALE * x_scale);
			double ty = y_mid_data - my / (SCALE * y_scale);
			double r = 5;
			double tol = (r / SCALE) * Math.max(1.0 / x_scale, 1.0 / y_scale);

			if ((vx - tx) * (vx - tx) + (vy - ty) * (vy - ty) < tol * tol)
				return v;
		}
		return result;
	}
}