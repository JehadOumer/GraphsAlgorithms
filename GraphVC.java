import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class GraphVC {
	
	private int v;
	private int AL[][];
	private int col[];
	private int deg[];
	private int k;
	
	GraphVC(int v, int n) {
		this.v=v;
		AL=new int[v][v];
		col=new int[v];
		deg=new int[v];
		k=n;
	}
	void addEdge(int v, int u) {
		AL[v][deg[v]]=u;
		AL[u][deg[u]]=v;
		deg[u]++;
		deg[v]++;
	}
	
	
	
	public boolean vc() {
		int tempDegV;
		if(k<0)
			return false;
		if(edgeless())
			return true;
		if(k==0)
			return false;
		v=selectVertex();
		if(deg[v]<3) {
			tempDegV=deg[v];
			deleteV(v);
			k--;
			return vc();
		}
			
		tempDegV=deg[v];
		deleteV(v);
		k--;			
		if(vc())
			return true;
		
		
		restoreV(v, tempDegV);
		
		for(int i=0;i<deg[v];i++) {
			deleteV(AL[v][i]);
		}
		k-=deg[v];
		return vc();
		
	}
	
	private boolean edgeless() {
		for (int i=0;i<deg.length;i++)
			if(deg[i] >0)
				return false;
		return true;
	}
	


	private void deleteV(int v) {
		int n;
		for (int i=0;i<deg[v];i++) {
			n=AL[v][i];
			for(int j=0;j<deg[n];j++) {
				if (deg[n] >0 && AL[n][j]==v) {
					AL[n][j]=AL[n][deg[n]-1];
					AL[n][deg[n]-1]=v;
					deg[n]--;
					break;
				}
			}
			
		}
		deg[v]=0;
	}
	private void restoreV(int v, int tempDegV) {
		int n;
		for (int i=0;i<tempDegV;i++) {
			n=AL[v][i];
			deg[n]++;
		}
		deg[v]=tempDegV;
	}
	
	private int selectVertex() {
		int maxIndex=0;
		for(int i=0;i<deg.length;i++)
			if (deg[i]>deg[maxIndex])
				maxIndex=i;
		return maxIndex;
	}
	
	
	
	
	public static void main(String[] args) {
		Scanner scan= new Scanner(System.in);
		int n=scan.nextInt();
		int e=scan.nextInt();
		int k=scan.nextInt();
		GraphVC graph= new GraphVC(n, k);
		for (int i=0;i<e;i++) {
			graph.addEdge(scan.nextInt(), scan.nextInt());
		}
//		graph.BFS(2);
//		graph.BFS();
//		boolean isBipartite= graph.isBipartite();
//		if(isBipartite) {
//			System.out.println("bipartite");
//		}
//		else {
//			System.out.println("not bipartite");
//		}
//		int[] deg = graph.getDeg();
//		int sumDeg=0;
//		for(int i=0;i<deg.length;i++) {
//			System.out.println(i+" : "+deg[i]);
//			sumDeg+=deg[i];
//		}
//		System.out.println("Sum of Degrees: "+sumDeg);
		boolean isVC= graph.vc();
		if(isVC) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}
		
		
	}

}
