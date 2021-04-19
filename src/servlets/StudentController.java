package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import dao.StudentDao;
import daoimpl.StudentDaoImpl;
import models.Student;

/**
 * Servlet implementation class StudentController
 */
@WebServlet("/StudentController")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	StudentDao studentDaoImpl = new StudentDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		System.out.println("Action : "+action);
		
		Gson gson = new Gson();

		if (action.equals("getAll")) {

			response.setContentType("javascript/json");

			List<Student> allStudents = studentDaoImpl.getAllStudent();

			String jsonList = gson.toJson(allStudents);

			// Convert java object to json
			JsonElement element = gson.toJsonTree(allStudents, new TypeToken<List<Student>>() {
			}.getType());
			JsonArray jsonArray = element.getAsJsonArray();

			String lsitData = jsonArray.toString();

			// Return json in the format required by jTable plugin

			lsitData = "{\"Result\":\"OK\", \"Records\":" + lsitData + "}";

			response.getWriter().print(lsitData);

		}

		if (action.equals("create")) {
			Student student = new Student();

			student.setName(request.getParameter("name"));
			student.setEmail(request.getParameter("email"));
			student.setContact(request.getParameter("contact"));
			student.setGender(request.getParameter("gender"));
			student.setCountry(request.getParameter("country"));

			studentDaoImpl.addStudent(student);

			Student student2 = studentDaoImpl.getStudentById(studentDaoImpl.getStudentIdByName(student.getName()));

			response.setContentType("javascript/json");

			// Convert java object to json

			String json = gson.toJson(student2);

			// Return json in the format required by jTable plugin

			String lsitData = "{\"Result\":\"OK\", \"Record\":" + json + "}";

			response.getWriter().print(lsitData);

		}
		
		if (action.equals("update")) {
			
			Integer id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			
			Student student = studentDaoImpl.getStudentById(id);

			student.setName(request.getParameter("name"));
			student.setEmail(request.getParameter("email"));
			student.setContact(request.getParameter("contact"));
			student.setGender(request.getParameter("gender"));
			student.setCountry(request.getParameter("country"));

			studentDaoImpl.updateStudent(student);


			response.setContentType("javascript/json");

			// Convert java object to json

			String json = gson.toJson(student);

			// Return json in the format required by jTable plugin

			String lsitData = "{\"Result\":\"OK\", \"Record\":" + json + "}";

			response.getWriter().print(lsitData);

		}
		
if (action.equals("delete")) {
			
			Integer id = Integer.parseInt(request.getParameter("id"));
			

			studentDaoImpl.deleteStudent(id);


			response.setContentType("javascript/json");

			// Return json in the format required by jTable plugin

			String lsitData = "{\"Result\":\"OK\"}";

			response.getWriter().print(lsitData);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
