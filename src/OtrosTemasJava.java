
public class OtrosTemasJava {

	public static void main(String[] args) {
		// For each o for mejorado
		int[] numeros= {10,4,5,9,13,21,45,0,73};
		for(int n:numeros) {
			System.out.print(n+",");
		}
		System.out.println(""+suma(9,1));
		
	}
		//Switch acepta strings
		
		//Varargs
	public static int suma(int...a) {//Puede haber más de una variable de Varargs pero solo un varargs y debe estar al final
		int res=0;
		for(int val:a) {
			res+=val;
		}
		return res;
	}


}
