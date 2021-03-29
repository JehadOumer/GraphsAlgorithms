import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class ISGraph {
	
	private int v;
	private int AL[][];
	private int col[];
	private int deg[];
	private int k;
	
	ISGraph(int v, int k) {
		this.v=v;
		AL=new int[v][v];
		col=new int[v];
		deg=new int[v];
		this.k=k;
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
	

public boolean IS() {
		if(k<=0)
			return true;
		if(isEmpty())
			return false;
			
		int v=selectVertex();
		if(deg[v]<3) {
			for(int i=0;i<deg[v];i++)
				deleteV(AL[v][i]);
			k--;
			return IS();
		}
		
		int tempDegV=deg[v];
		int tempDegU[]=new int[v];
		
		for(int i=0;i<deg[v];i++) {
			tempDegU[i]=deg[AL[v][i]];
			deleteV(AL[v][i]);
		}
		if(IS())
			return true;
		
		for(int i=0;i<tempDegV;i++)
			restoreV(v,tempDegU[i]);
		k++;
		deleteV(v);
		return IS();

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
	
	private int selectVertex() {
		int max=0;
		for(int i=0;i<deg.length;i++)
			if (deg[i]>-1 && deg[i]>deg[max])
				max=i;
		return max;
	}
	
	private boolean isEmpty() {
		for(int i=0;i<deg.length;i++)
			if(deg[i]!=-1)
				return false;
		return true;
	}
	
	
	public static void main(String[] args) {
		Scanner scan= new Scanner(System.in);
		int n=scan.nextInt();
		int e=scan.nextInt();
		ISGraph graph= new ISGraph(n,e);
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
		boolean isIS= graph.IS();
		if(isIS) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}
		
		
	}

}
