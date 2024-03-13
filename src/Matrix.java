

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.ArrayList;

public class Matrix{
	List<List> data = new ArrayList<List>();
	
	private Random random = new Random();
	private int max = 1;
	private int min = -1;
	private int columns = 0;
	private int rows = 0;
	
	public Matrix(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		for(int i = 0; i < columns; i++) {
			List<Double> innerMatrix = new ArrayList<Double>();
			data.add(innerMatrix);
			for(int j = 0; j < rows; j++) {
				innerMatrix.add(0.0);
			}
		}
	}
	
	public static Matrix fromArray(double[] arr) {
		Matrix m = new Matrix(arr.length, 1);
		for(int i = 0; i < arr.length; i++) {
			m.data.get(i).set(0, arr[i]);
		}
		return m;
	}
	
	public double[] toArray() {
		int size = this.data.size();
		double[] arr = new double[size];
		for(int i = 0; i < this.data.size(); i++) {
			for(int j = 0; j < this.data.get(0).size(); j++) {
				arr[j] = (double)this.data.get(i).get(j);
			}
		}
		return arr;
	}
	
	public void randomize() {
		for(int i = 0; i < data.size() ; i++) {
			for(int j = 0; j < data.get(i).size(); j++) {
				double element = (double) (Math.random()*(max-min)+min);
				data.get(i).set(j, element);
			}
		}
	}
	
	// �������̽��� ���� �Լ��� �Ű������� �޾� ����� ������ ������ �� �Լ��� ����
	public Matrix map(Map m) {
		for(int i = 0; i < data.size(); i++) {
			for(int j = 0; j < data.get(i).size(); j++) {
				double val = (double)data.get(i).get(j);
				data.get(i).set(j, m.map(val));
			}
		}
		return this;
	}
	
	public static Matrix map(Matrix matrix,Map m) {
		Matrix n_Matrix = matrix;
		for(int i = 0; i < n_Matrix.data.size(); i++) {
			for(int j = 0; j < n_Matrix.data.get(i).size(); j++) {
				double val = (double)n_Matrix.data.get(i).get(j);
				n_Matrix.data.get(i).set(j, m.map(val));
			}
		}
		return n_Matrix;
	}

/*
	// data plus number ��İ� ���� ����
	public void add(int num) {
		for(int i = 0; i < data.size() ; i++) {
			for(int j = 0; j < data.get(i).size(); j++) {
				int element = (int)data.get(i).get(j)+num;
				data.get(i).set(j, element);
			}
		}
	}
	
	// data product number ��İ� ���� ����
	public void multiply(int num) {
		for(int i = 0; i < data.size() ; i++) {
			for(int j = 0; j < data.get(i).size(); j++) {
				int element = (int)data.get(i).get(j)*num;
				data.get(i).set(j, element);
			}
		}
	}
	*/
	
	// data plus data ��İ� ��� ����
	public Matrix add(Matrix n) {
		Matrix n_Matrix = new Matrix(this.data.size(),this.data.get(0).size());
		int m1ColumnSize = this.data.size();
		int m2ColumnSize = n.data.size();
		int m1RowSize = this.data.get(0).size();
		int m2RowSize = n.data.get(0).size();
		if(m1ColumnSize == m2ColumnSize && m1RowSize == m2RowSize) {	
			for(int i = 0; i < this.data.size() ; i++) {
				for(int j = 0; j < this.data.get(i).size(); j++) {
					double element = (double)this.data.get(i).get(j)+(double)n.data.get(i).get(j);
					n_Matrix.data.get(i).set(j, element);
				}
			}
			//n_Matrix.PrintMatrix();
			return n_Matrix;
		}else {
			System.out.println("m1�� ���� ���� m2�� ���� ���� �����ּ���");
			return null;
		}
	}
	
	// data plus data ��İ� ��� ����
		public Matrix subtract(Matrix n) {
			Matrix n_Matrix = new Matrix(this.data.size(),this.data.get(0).size());
			int m1ColumnSize = this.data.size();
			int m2ColumnSize = n.data.size();
			int m1RowSize = this.data.get(0).size();
			int m2RowSize = n.data.get(0).size();
			if(m1ColumnSize == m2ColumnSize && m1RowSize == m2RowSize) {	
				for(int i = 0; i < this.data.size() ; i++) {
					for(int j = 0; j < this.data.get(i).size(); j++) {
						double element = (double)this.data.get(i).get(j)-(double)n.data.get(i).get(j);
						n_Matrix.data.get(i).set(j, element);
					}
				}
				//n_Matrix.PrintMatrix();
				return n_Matrix;
			}else {
				System.out.println("m1�� ���� ���� m2�� ���� ���� �����ּ���");
				return null;
			}
		}
	
	// data product data ��İ� ��� ����
	public static Matrix multiply(Matrix a,Matrix b) {
		Matrix n_Matrix = new Matrix(a.data.size(),b.data.get(0).size());
		int m1ColumnSize = a.data.get(0).size();
		int m2RowSize = b.data.size();
		if(m1ColumnSize == m2RowSize) {
			for(int i = 0; i < n_Matrix.data.get(0).size();i++) {
				for(int j = 0; j < n_Matrix.data.size();j++) {
					double sum = 0;
					for(int t = 0; t < a.data.get(0).size();t++) {
						sum+= (double)a.data.get(j).get(t)*(double)b.data.get(t).get(i);
					}
					n_Matrix.data.get(j).set(i, sum);
				}
				
			}
//			n_Matrix.PrintMatrix();
			return n_Matrix;
		}else {
			System.out.println("m1�� �� ���� m2�� �� ���� �����ּ���");
			return a;
		}
	}
	// �� ������ �׸��� ���Ѵ�
	public void multiply(Matrix a) {
		int thisColumnSize = this.data.get(0).size();
		int thisRowSize = this.data.size();
		int aColumnSize = a.data.get(0).size();
		int aRowSize = a.data.size();
		if(thisColumnSize == aColumnSize && thisRowSize == aRowSize) {
			for(int i = 0; i < thisRowSize; i++) {
				for(int j = 0; j < thisColumnSize; j++) {
					this.data.get(i).set(j, (double)this.data.get(i).get(j)*(double)a.data.get(i).get(j));
				}
			}
		}else {
			System.out.println("���� ������ �׸� ���� �����ּ���");
		}
	}
	
	
	// ����� ���� ��Ų�� ���� ���� ���� ������
	public static Matrix transpose(Matrix matrix) 
	{
		Matrix n_Matrix = new Matrix(matrix.data.get(0).size(),matrix.data.size());
		for(int i = 0; i < matrix.data.size(); i++) {
			for(int j = 0; j < matrix.data.get(0).size(); j++) {
				n_Matrix.data.get(j).set(i, matrix.data.get(i).get(j));
			}
		}
		
		return n_Matrix;
	}
	
	public double calcAverage() {
		double avarage = 0.0;
		double totalSize = 0.0;
		for(int i = 0; i < this.data.size(); i++) {
			for(int j = 0; j < this.data.get(0).size(); j++) {
				avarage += (double)this.data.get(i).get(j);
				totalSize++;
			}
		}
		avarage /= totalSize;
		return avarage;
	}

	// ����� ����Ѵ�
	public void PrintMatrix() {
		for(int i = 0; i < data.size(); i++) {
			for(int j = 0; j < data.get(i).size(); j++) {
				System.out.print(data.get(i).get(j)+" ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
