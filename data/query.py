import predictionio
engine_client = predictionio.EngineClient(url="http://localhost:8000")
print engine_client.send_query({"plan":1,"regwt":2,"reqsize":1,"reqquality":3})
