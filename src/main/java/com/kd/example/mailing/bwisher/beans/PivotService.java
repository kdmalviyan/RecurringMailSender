package com.kd.example.mailing.bwisher.beans;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PivotService {
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Getting Employee data from pivot for a specific day.
	 * 
	 * @param date
	 * @return
	 */
	public ArrayNode getEmployees(Date date) {
		ObjectNode node1 = mapper.createObjectNode();
		node1.put("emailId", "kdmalviyan@gmail.com");
		node1.put("name", "Kuldeep Singh");
		node1.put("picUrl", "https://upload.wikimedia.org/wikipedia/commons/e/e5/Very_large_array_clouds.jpg");

		ArrayNode jsonNode = mapper.createArrayNode();
		jsonNode.add(node1);
		return jsonNode;
	}

}
