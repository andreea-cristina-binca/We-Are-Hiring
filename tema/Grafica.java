package tema;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

public class Grafica {
	public static void MainPage(JFrame fr) {
		fr.getContentPane().removeAll();
		fr.repaint();
		fr.setLayout(new GridLayout(3,1));
		
		JPanel up = new JPanel();
		JPanel mid = new JPanel();
		JPanel down = new JPanel();
		
		fr.add(up);
		fr.add(mid);
		fr.add(down);
		
		mid.setLayout(new GridLayout(1,3,20,20));
		
		JButton adm = new JButton("Admin Page");
		JButton man = new JButton("Manager Page");
		JButton pro = new JButton("Profile Page");
		
		ActionListener aladm = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Grafica.AdminPage(fr);
			}
		};
		
		ActionListener alman = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Grafica.SearchPage(fr, 0);
			}
		};
		
		ActionListener alpro = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Grafica.SearchPage(fr, 1);
			}
		};
		
		adm.addActionListener(aladm);
		man.addActionListener(alman);
		pro.addActionListener(alpro);
		
		mid.add(adm);
		mid.add(man);
		mid.add(pro);
		
		fr.pack();
	}
	
	public static void SearchPage(JFrame fr, int where) {
		fr.getContentPane().removeAll();
		fr.repaint();
		fr.setLayout(new GridLayout(3,1));
		
		JPanel up = new JPanel();
		JPanel mid = new JPanel();
		JPanel down = new JPanel();
		
		fr.add(up);
		fr.add(mid);
		fr.add(down);
		
		up.setLayout(new FlowLayout());
		mid.setLayout(new FlowLayout());
		down.setLayout(new GridLayout());
		
		JTextField txt = new JTextField(50);
		JButton btn = new JButton("Open");
		JButton back = new JButton("Back");
		JTextField comm = new JTextField();
		comm.setEditable(false);
		comm.setHorizontalAlignment(JTextField.CENTER);
		
		up.add(back);
		mid.add(txt);
		mid.add(btn);
		down.add(comm);
		
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = txt.getText();
				if (s.isEmpty()) {
					comm.setText("No input!");
					return;
				}
				int opened = 0;
				Application app = Application.getInstance();
				for (Company i : app.companies) {
					if (where == 1) {
						for(Department j : i.departments) {
							for (Employee k : j.employees) {
								if (s.equals(k.resume.info.getFirstName() + " " + k.resume.info.getLastName())) {
									opened = 1;
									Grafica.ProfilePage(fr, k);	
								}
							}
						}
					}
					else {
						Department man = i.departments.get(1);
						for (Employee j : man.employees) {
							if (s.equals(j.resume.info.getFirstName() + " " + j.resume.info.getLastName())) {
								opened = 1;
								Grafica.ManagerPage(fr, j);
							}
						}
					}			
				}
				for (User i : app.users) {
					if (s.equals(i.resume.info.getFirstName() + " " + i.resume.info.getLastName())) {
						if (where == 1) {
							opened = 1;
							Grafica.ProfilePage(fr, i);
						}
					}
				}
				if (opened == 0) {
					if (where == 0) {
						comm.setText("Input name is not a manager! Access denied!");
						return;
					}
					else
						comm.setText("Input name not found!");
				}
			}
		};
		
		ActionListener alback = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grafica.MainPage(fr);
			}
		};
		
		btn.addActionListener(al);
		back.addActionListener(alback);
		
		fr.pack();
	}
	
	public static void AdminPage(JFrame fr) {
		fr.getContentPane().removeAll();
		fr.repaint();
		fr.setLayout(new GridLayout(1,2));
		
		DefaultMutableTreeNode tr = new DefaultMutableTreeNode();
		
		Application app = Application.getInstance();
		
		tr.setUserObject("Application");
		DefaultMutableTreeNode nod = new DefaultMutableTreeNode("Users");
		tr.add(nod);
		for (User i : app.users) {
			DefaultMutableTreeNode new_nod = new DefaultMutableTreeNode(i.resume.info.getFirstName() + " " + i.resume.info.getLastName());
			nod.add(new_nod);
		}
		nod = new DefaultMutableTreeNode("Companies");
		tr.add(nod);
		for (Company i : app.companies) {
			DefaultMutableTreeNode new_nod = new DefaultMutableTreeNode(i.name);
			nod.add(new_nod);
			for (Department j : i.departments) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(j.getClass().getSimpleName());
				new_nod.add(child);
				DefaultMutableTreeNode emp = new DefaultMutableTreeNode("Employees");
				child.add(emp);
				for (Employee k : j.employees) {
					DefaultMutableTreeNode new_child = new DefaultMutableTreeNode(k.resume.info.getFirstName() + " " + k.resume.info.getLastName());
					emp.add(new_child);
				}
				if (!j.availablejobs.isEmpty()) {
					DefaultMutableTreeNode job = new DefaultMutableTreeNode("Available jobs");
					child.add(job);
					for (Job k : j.availablejobs) {
						DefaultMutableTreeNode new_child = new DefaultMutableTreeNode(k.jobname);
						job.add(new_child);
					}
				}
			}
		}
		
		JTree jtree = new JTree(tr);
		
		JScrollPane scp = new JScrollPane(jtree);
		
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(2,1));
		
		JPanel upperpan = new JPanel();
		upperpan.setLayout(new GridLayout(2,1));
		JPanel lowerpan = new JPanel();
		lowerpan.setLayout(new GridLayout(1,1));
		
		pan.add(upperpan);
		pan.add(lowerpan);
		
		JButton back = new JButton("Back");
		JButton btn = new JButton("Calculate");
		btn.setEnabled(false);
		
		JTextField txt = new JTextField();
		txt.setEditable(false);
		txt.setHorizontalAlignment(JTextField.CENTER);
		
		upperpan.add(back);
		upperpan.add(btn);
		lowerpan.add(txt);
		fr.add(scp);
		fr.add(pan);
		
		TreeSelectionListener tsl = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				String s = jtree.getLastSelectedPathComponent().toString();
				if (s.equals("IT") || s.equals("Management") || s.equals("Marketing") || s.equals("Finance"))
					btn.setEnabled(true);
				else
					btn.setEnabled(false);
				txt.setText("");
			}
		};
		
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode nod = (DefaultMutableTreeNode)(jtree.getLastSelectedPathComponent());
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode)(nod.getParent());
				if (parent.toString().equals("Google")) {
					for (Department i : app.companies.get(0).departments) {
						if (i.getClass().getSimpleName().equals(nod.getUserObject())) {
							txt.setText("Google: " + i.getClass().getSimpleName() + " salary: " + i.getTotalSalaryBudget());
						}
					}
				}
				else {
					for (Department i : app.companies.get(1).departments) {
						if (i.getClass().getSimpleName().equals(nod.getUserObject())) {
							txt.setText("Amazon: " + i.getClass().getSimpleName() + " salary: " + i.getTotalSalaryBudget());
						}
					}
				}
				btn.setEnabled(false);
			}
		};
		
		ActionListener alback = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grafica.MainPage(fr);
			}
		};
		
		jtree.addTreeSelectionListener(tsl);
		btn.addActionListener(al);
		back.addActionListener(alback);
		
		fr.pack();
	}
	
	public static void ManagerPage(JFrame fr, Consumer man) {
		fr.getContentPane().removeAll();
		fr.repaint();
		fr.setLayout(new GridLayout(1,2));
		
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(3,1));
		JButton back = new JButton("Back");
		JButton accept = new JButton("Accept");
		accept.setEnabled(false);
		JButton deny = new JButton("Deny");
		deny.setEnabled(false);
		left.add(back);
		left.add(accept);
		left.add(deny);
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(((Manager)man).requests.size()*2,1));
		JScrollPane scp = new JScrollPane(right);
		
		fr.add(left);
		fr.add(scp);
		
		ActionListener alback = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grafica.MainPage(fr);
			}
		};

		back.addActionListener(alback);	
		
		ButtonGroup btngrp = new ButtonGroup();
		ArrayList<JRadioButton> reqbtns = new ArrayList<JRadioButton>();
		ArrayList<JTextArea> reqtxts = new ArrayList<JTextArea>();
		for (Request<Job, Consumer> i : ((Manager)man).requests) {
			JRadioButton btn = new JRadioButton();
			btngrp.add(btn);
			reqbtns.add(btn);
			btn.setText("Request "+ (reqbtns.indexOf(btn) + 1));
			JTextArea txt = new JTextArea(10,10);
			reqtxts.add(txt);
			txt.setEditable(false);
			String s = "";
			s = s + "Request:" + (reqbtns.indexOf(btn)+1) + "\n";
			s = s + "\tPosition: " + i.getKey().jobname + "\n";
			s = s + "\tApplicant: " + i.getValue1().resume.info.getFirstName() + " " + i.getValue1().resume.info.getLastName() + "\n";
			s = s + "\tRecruiter: " + i.getValue2().resume.info.getFirstName() + " " + i.getValue2().resume.info.getLastName()  + "\n";
			s = s + "\tScore: " + i.getScore() + "\n";
			txt.setText(s);
			right.add(btn);
			right.add(txt);
		}
		
		ActionListener chosen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accept.setEnabled(true);
				deny.setEnabled(true);
			}
		};
		
		for (JRadioButton i : reqbtns) {
			i.addActionListener(chosen);
		}
		
		ActionListener alaccept = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JRadioButton i : reqbtns) {
					if (i.isSelected()) {
						Request<Job, Consumer> r = ((Manager)man).requests.get(reqbtns.indexOf(i));
						
						if (Application.getInstance().users.contains(r.getValue1())) {
							Employee newemp = ((User)r.getValue1()).convert();
							newemp.compname = r.getKey().companyname;
							newemp.salary = r.getKey().givensalary;
							Company comp = Application.getInstance().getCompany(r.getKey().companyname);
							for (Department j : comp.departments) {
								if (j.getClass().getSimpleName().equals(r.getKey().deptname))
									j.employees.add(newemp);
							}
							Application.getInstance().users.remove(r.getValue1());
							for(Company j : Application.getInstance().companies) {
								j.removeObserver((User)r.getValue1());
								
								for (Employee k : j.departments.get(1).employees) {
									if (j.manager.equals(k.resume.info.getFirstName() + " " + k.resume.info.getLastName())) {
										Manager currentman = (Manager)k;
										ArrayList<Request<Job, Consumer>> delreq = new ArrayList<Request<Job, Consumer>>();
										
										for (Request<Job, Consumer> l : currentman.requests) {
											if (l.getValue1().equals(r.getValue1())) {
												delreq.add(l);
											}
										}
										
										for (Request<Job, Consumer> l : delreq) {
											currentman.requests.remove(l);
										}
									}
								}
								
							}
							r.getKey().noapplicants--;
						}
						
						if(r.getKey().noapplicants == 0) {
							ArrayList<Request<Job, Consumer>> del = new ArrayList<Request<Job, Consumer>>();
							for (Request<Job, Consumer> j : ((Manager)man).requests) {
								if (j.getKey().jobname.equals(r.getKey().jobname)) {
									del.add(j);
								}
							}
							for (Request<Job, Consumer> j : del) {
								((Manager)man).requests.remove(j);
							}
							r.getKey().open = false;
						}
						
						Grafica.ManagerPage(fr, man);
					}
				}
			}
		};
		
		ActionListener aldeny = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JRadioButton i : reqbtns) {
					if (i.isSelected()) {
						((Manager)man).requests.remove(reqbtns.indexOf(i));
						Grafica.ManagerPage(fr, man);
					}
				}
			}
		};
		
		accept.addActionListener(alaccept);
		deny.addActionListener(aldeny);
		
		fr.pack();
	}
	
	public static void ProfilePage(JFrame fr, Consumer user) {
		fr.getContentPane().removeAll();
		fr.repaint();
		fr.setLayout(new FlowLayout());
		
		JButton back = new JButton("Back");
		
		JTextArea txt = new JTextArea(30,50);
		txt.setEditable(false);
		
		JScrollPane scp = new JScrollPane(txt);
		
		fr.add(back);
		fr.add(scp);
		
		ActionListener alback = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grafica.MainPage(fr);
			}
		};
		
		back.addActionListener(alback);
		
		txt.setText(user.resume.toString());
		
		fr.pack();
	}
	
	public static void main (String args[]){ 
		JFrame fr = new JFrame();		
		
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setMinimumSize(new Dimension(800,600));
		fr.setMaximumSize(new Dimension(800,600));
		fr.pack();
		
		Test.startApplication();
		
		Grafica.MainPage(fr);
		fr.pack();
    }
}
