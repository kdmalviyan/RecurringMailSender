package com.kd.bwisher.beans;

import java.util.Date;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PivotService {
	private ObjectMapper mapper = new ObjectMapper();

	public ArrayNode getEmployees(Date date) {
		ArrayNode jsonNode = mapper.createArrayNode();
		RestTemplate restTemplate = new RestTemplate();
		String endPoint = "http://gturnquist-quoters.cfapps.io/api";
		JsonNode employeesData = restTemplate.getForObject(endPoint, JsonNode.class);
		if (employeesData.isArray()) {
			// Uncomment when actual data is available from source
			// jsonNode = (ArrayNode) employeesData;
		}
		// Delete below code except return statement, once actual data is
		// available
		ObjectNode node1 = mapper.createObjectNode();
		node1.put("emailId", "kuldeep.singh@impetus.co.in");
		node1.put("name", "Kuldeep Singh");
		node1.put("picUrl", "https://pivot.impetus.co.in/digite/upload/kuldeep.singh.jpg");
		jsonNode.add(node1);

		return jsonNode;
	}

}
