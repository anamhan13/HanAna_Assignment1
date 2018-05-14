package client.listener;

import client.model.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.model.Article;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Observer;



public class ServerListener extends Thread {
	private ObjectInputStream input;
	private Observer view;

	
	public ServerListener(ObjectInputStream input) {
		this.input=input;
		//this.view=view;
	}

	public void run() {

		try {
			while(true){
				String o = (String) input.readObject();
				if (o != null) {
					Gson gson = new GsonBuilder().create();
					Object userObject = gson.fromJson(o, Object.class);

					if (userObject.getClass().equals(client.model.Writer.class)) {
						Writer writer = (Writer) userObject;
						System.out.println(writer.getName());
					} else if (userObject.getClass().equals(Article.class)) {
					    Article article = (Article) userObject;
                        System.out.println("yay article");
					} else if (userObject.getClass().toString().equals("class java.util.ArrayList")) {
					    List<Article> articleList = (List<Article>) userObject;
                        System.out.println(articleList.size());
                    }
                } else {

				}
				//view.update(null, o);
			}
		} catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public void setInput(ObjectInputStream input) {
		this.input = input;
	}

	public Observer getView() {
		return view;
	}

	public void setView(Observer view) {
		this.view = view;
	}
}
