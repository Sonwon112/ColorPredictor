

public class NeuralNetwork {
	int input_node;
	int hidden_node;
	int output_node;

	Matrix weight_ih;
	Matrix weight_ho;

	Matrix bias_h;
	Matrix bias_o;

	double learning_Rate = 0.8;

	public NeuralNetwork(int input_node, int hidden_node, int output_node) {
		this.input_node = input_node;
		this.hidden_node = hidden_node;
		this.output_node = output_node;

		weight_ih = new Matrix(this.hidden_node, this.input_node);
		weight_ho = new Matrix(this.output_node, this.hidden_node);
		weight_ih.randomize();
		weight_ho.randomize();

		bias_h = new Matrix(this.hidden_node, 1);
		bias_o = new Matrix(this.output_node, 1);
		bias_h.randomize();
		bias_o.randomize();
	}

	public double[] feedForward(double[] input_arr) {

		Matrix input = Matrix.fromArray(input_arr); // �迭�� �޾ƿ� input_arr�� Matrix ���·� �ٲ�

		// hidden Layer�� ���� ������
		Matrix hidden = Matrix.multiply(weight_ih, input);
		hidden = hidden.add(bias_h);
		hidden = hidden.map(new sigmoid());

		// output Layer�� ���� ������
		Matrix output = Matrix.multiply(weight_ho, hidden);
		output = output.add(bias_o);
		output = output.map(new sigmoid());

		return output.toArray(); // �迭�� �ٲپ� ���� ��ȯ
	}

	public void train(double[] input_arr, double[] target_arr) {
		Matrix input = Matrix.fromArray(input_arr); // �迭�� �޾ƿ� input_arr�� Matrix ���·� �ٲ�

		// hidden Layer�� ���� ������
		Matrix hidden = Matrix.multiply(weight_ih, input);
		hidden = hidden.add(bias_h);
		hidden = hidden.map(new sigmoid());

		// output Layer�� ���� ������
		Matrix output = Matrix.multiply(weight_ho, hidden);
		output = output.add(bias_o);
		output = output.map(new sigmoid());

		Matrix m_targets = Matrix.fromArray(target_arr); // Matrix ������ ���� target�� Matrix�� ��ȯ

		// output Layer�� ������ ����
		Matrix output_errors = m_targets.subtract(output);
//		double cost_ouput_errors = output_errors.calcAverage();

		// weight_ho�� �����ϴ� �κ�-----------------------------------------
		Matrix output_gradient = Matrix.map(output, new dsigmoid());
		output_gradient.multiply(output_errors);
		output_gradient = Matrix.map(output_gradient, new multiply(learning_Rate));

		Matrix hidden_t = Matrix.transpose(hidden);
		Matrix deltaWeight_ho = Matrix.multiply(output_gradient, hidden_t);

		weight_ho = weight_ho.add(deltaWeight_ho);
		bias_o = bias_o.add(output_gradient);
		// ------------------------------------------------------------

		// hidden Layer�� ������ ����
		Matrix t_weight_ho = Matrix.transpose(weight_ho);
		Matrix hidden_errors = Matrix.multiply(t_weight_ho, output_errors);
//		double cost_hidden_errors = hidden_errors.calcAverage();

		// weight_ih�� �����ϴ� �κ�-----------------------------------------
		Matrix hidden_gradient = Matrix.map(hidden, new dsigmoid());
		hidden_gradient.multiply(hidden_errors);
		hidden_gradient = Matrix.map(hidden_gradient, new multiply(learning_Rate));

		Matrix input_t = Matrix.transpose(input);
		Matrix deltaWeight_ih = Matrix.multiply(hidden_gradient, input_t);

		weight_ih = weight_ih.add(deltaWeight_ih);
		bias_h = bias_h.add(hidden_gradient);
		// ------------------------------------------------------------

		//input.PrintMatrix();
		//output.PrintMatrix();
		//output_errors.PrintMatrix();
		//hidden_errors.PrintMatrix();
		
	}
	
}

class sigmoid implements Map {
	public double map(double n) {
		return 1 / (1 + Math.exp(-n));
	}
}

class dsigmoid implements Map {
	public double map(double n) {
		sigmoid s = new sigmoid();
		return n * (1 - n);
	}
}

class multiply implements Map {
	double num = 0;

	public multiply(double num) {
		this.num = num;
	}

	public double map(double n) {
		return n * num;
	}
}
