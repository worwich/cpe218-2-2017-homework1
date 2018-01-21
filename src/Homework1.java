import java.util.Stack;
public class Homework1
{
	public static node tree;
	public static Stack <Character>Bank = new Stack<Character>();
	public static void main(String[] args)
	{
		// Begin of arguments input sample
		/*if (args.length > 0)
		{
			String input = args[0];
			if (input.equalsIgnoreCase("251-*32*+"))
			{
				System.out.println("(2*(5-1))+(3*2)=14");
			}
		}*/

		String input="251-*32*+";
		for(int i=0;i<input.length();i++)
		{
			Bank.add(input.charAt(i));
		}
		tree = new node(Bank.pop());
		infix(tree);
		inorder(tree);
		System.out.println("="+calculate(tree));

		// End of arguments input sample
		// TODO: Implement your project here
	}
	public static void inorder(node a)
	{
		if(a.star=='+')
		{
			if(a!=tree)System.out.print("(");
			inorder(a.left);
			System.out.print('+');
			inorder(a.right);
			if(a!=tree)System.out.print(")");
		}
		else if(a.star=='-')
		{
			if(a!=tree)System.out.print("(");
			inorder(a.left);
			System.out.print('-');
			inorder(a.right);
			if(a!=tree)System.out.print(")");
		}
		else if(a.star=='*')
		{
			if(a!=tree)System.out.print("(");
			inorder(a.left);
			System.out.print('*');
			inorder(a.right);
			if(a!=tree)System.out.print(")");
		}
		else if(a.star=='/')
		{
			if(a!=tree)System.out.print("(");
			inorder(a.left);
			System.out.print('/');
			inorder(a.right);
			if(a!=tree)System.out.print(")");
		}
		else
		{
			System.out.print(a.star);
		}
	}
	public static void infix(node a)
	{
		if(a.star=='+'||a.star=='-'||a.star=='*'||a.star=='/')
		{
			a.right=new node(Bank.pop());
			infix(a.right);
			a.left=new node(Bank.pop());
			infix(a.left);
		}
	}
	public static int calculate(node a)
	{
		if(a.star=='+')
		{
			return calculate(a.left) + calculate(a.right);
		}
		if(a.star=='-')
		{
			return calculate(a.left) - calculate(a.right);
		}
		if(a.star=='*')
		{
			return calculate(a.left) * calculate(a.right);
		}
		else return Integer.parseInt(a.star.toString());
	}
	public class node
	{
		node right;
		node left;
		Character star;
		node(char b)
		{
			star=b;
		}
	}

}
