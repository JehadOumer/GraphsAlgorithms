import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph3Col6 {
	
	private int v;
	private int AL[][];
	private int col[];
	private int deg[];
	
	Graph3Col6(int v) {
		this.v=v;
		AL=new int[v][v];
		col=new int[v];
		deg=new int[v];
	}
	void addEdge(int v, int u) {
		if(v==u) {
			AL[v][deg[v]]=u;
			deg[v]++;
		}
		else {
			AL[v][deg[v]]=u;
			AL[u][deg[u]]=v;
			deg[u]++;
			deg[v]++;

		}
		
	}

	boolean isBipartite() {
		//boolean[] visited= new boolean[v];
		int[] colors = new int[v];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		for (int i=0;i<this.v;i++) {
			if(deg[i]>-1 && colors[i]==0) {
				//int j=i;
				//visited[j]=true;
				colors[i]=1;
				queue.add(i);
				while(!queue.isEmpty()) {
					int j=queue.poll();
					for (int q=0;q<deg[j];q++) {
						if(deg[AL[j][q]]>-1) {
							if(colors[AL[j][q]]==0) {
								colors[AL[j][q]]=-colors[j];
								queue.add(AL[j][q]);
							}
							else if(colors[AL[j][q]] == colors[j])
								return false;
							//colors[AL[j][q]]=-colors[j];
						}
							
						}
						
					}
			}
			
	}
		
		
	//}
	
		return true;
	}
	

public boolean Color3() {
		int tempDegV;
		
		if(isAllVColored4())
			return isBipartite();
		
		int u=selectVertex();

		if(allVNColored4(u)) {
			col[u]=1;
			//tempDegV=deg[u];
			deleteV(u);
			return Color3();

		}
			
		
		col[u]=1;
		colorU4(u);
		
		tempDegV=deg[u];
		
		deleteV(u);
		if (Color3())
			return true;
		
		restoreV(u, tempDegV);
		col[u]=4;
		
		return Color3();
	}
	private void colorU4(int u) {
		for(int i=0;i<deg[u];i++)
			if (deg[AL[u][i]] >-1 && col[AL[u][i]] == 0)
				col[AL[u][i]]=4;
	}


	private void deleteV(int u) {
		int n;
		for (int i=0;i<deg[u];i++) {
			n=AL[u][i];
			if(deg[n]>0) {
				for(int j=0;j<deg[n];j++) {
					if (AL[n][j]==u) {
						AL[n][j]=AL[n][deg[n]-1];
						AL[n][deg[n]-1]=u;
						deg[n]--;
						break;
					}
				}
			}
			
			
		}
		deg[u]=-1;
	}
	private void restoreV(int u, int tempDegV) {
		//int count=0;
		for (int i=0;i<tempDegV;i++) {
			if(deg[AL[u][i]]>-1) {
				deg[AL[u][i]]++;
				//count++;
			}
			
		}
		deg[u]=tempDegV;
		
	}
	
	private boolean isAllVColored4(){
		for(int i=0;i<col.length;i++)
			if (deg[i]>-1 && col[i] != 4 )
				return false;
		return true;
	}
	
	private boolean allVNColored4(int u) {
		for(int i=0;i<deg[u];i++) 
			if(deg[AL[u][i]]>-1 && col[ AL[u][i] ] != 4 )
				return false;
		return true;
	}
	
//	private int selectVertex() {
//		int max=0;
//		int count;
//		int vertex=-1;
//		for (int i=0;i<col.length;i++){
//			count=0;
//			if(deg[i]>-1 && col[i]==0) 
//				for (int j=0;j<deg[i];j++) 
//					if(col[AL[i][j]] == 0  )
//						count++;
//			
//				
//			if(count>max) {
//				max=count;
//				vertex=i;
//			}
//			
//		}
//		return vertex;    
//	}
	private int selectVertex() {
		for(int i=0;i<col.length;i++)
			if (deg[i]>-1 && col[i] ==0)
				return i;
		return -1;
	}
	
	
	
	public static void main(String[] args) {
		Scanner scan= new Scanner(System.in);
		int n=scan.nextInt();
		int e=scan.nextInt();
		Graph3Col6 graph= new Graph3Col6(n);
		for (int i=0;i<e;i++) {
			graph.addEdge(scan.nextInt(), scan.nextInt());
		}
//		boolean isBipartite= graph.isBipartite();
//		if(isBipartite) {
//			System.out.println("bipartite");
//		}
//		else {
//			System.out.println("not bipartite");
//		}
		boolean is3Colorable= graph.Color3();
		if(is3Colorable) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}
		
		
	}

}
