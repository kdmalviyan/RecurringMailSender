package com.impetus.bwisher.beans;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PivotService {
	private ObjectMapper mapper = new ObjectMapper();

	public ArrayNode getEmployees(Date date) {
		ObjectNode node1 = mapper.createObjectNode();
		node1.put("emailId", "sunil.gupta@impetus.co.in");
		node1.put("name", "Kuldeep Singh");
		node1.put("picUrl", "https://pivot.impetus.co.in/digite/upload/kuldeep.singh.jpg");
		/*
		 * ObjectNode node2 = mapper.createObjectNode(); node2.put("emailId",
		 * "sunil.gupta@impetus.co.in"); node2.put("name", "Sunil Gupta");
		 * node2.put("picUrl",
		 * "http://www.impetus.com/sites/default/files/Praveen_leadership_0.png"
		 * );
		 */
		ArrayNode jsonNode = mapper.createArrayNode();
		jsonNode.add(node1);
		// jsonNode.add(node2);
		return jsonNode;
	}

}
