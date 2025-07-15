package me.nao.generalinfo.mg;



public class TimeRecord {

	
	
   private String name;
   private long seconds;
  
   
    public TimeRecord(String nombre, String tiempo) {
        this.name = nombre;
        String[] partes = tiempo.split(":");
        this.seconds = Integer.parseInt(partes[0]) * 3600 + Integer.parseInt(partes[1]) * 60 + Integer.parseInt(partes[2]);
    }

    public String getCronometPlayerName() {
        return name;
    }

    public long getCronometTotalSeconds() {
        return seconds;
    }

    public void setNewRecord(String name , long segundos) {
    	this.name = name;
    	this.seconds = segundos;
    }
    
    public String getCronometTime() {
        long horas = seconds / 3600;
        long minutos = (seconds % 3600) / 60;
        long segundosRestantes = seconds % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
    }
}















class Tiempo {
    String nombre;
    long segundos;
    String cronomet;

    public Tiempo(String nombre, String tiempo) {
        this.nombre = nombre;
        String[] partes = tiempo.split(":");
        this.segundos = Integer.parseInt(partes[0]) * 3600 + Integer.parseInt(partes[1]) * 60 + Integer.parseInt(partes[2]);
    }

    public String getNombre() {
        return nombre;
    }

    public long getSegundos() {
        return segundos;
    }
    
    public void setNewRecord(String nombre , long segundos) {
    	this.nombre = nombre;
    	this.segundos = segundos;
    }

    public String getTiempo() {
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        long segundosRestantes = segundos % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
    }
    
}




//List<Tiempo> registros = new ArrayList<>();
//registros.add(new Tiempo("Luis", "00:25:00"));
//registros.add(new Tiempo("Maria", "00:32:43"));
//registros.add(new Tiempo("Marcos", "00:27:34"));
//registros.add(new Tiempo("Tito", "00:35:30"));
//registros.add(new Tiempo("Karely", "00:50:34"));
////registros.add(new Tiempo("Rui", "00:45:20"));
////registros.add(new Tiempo("Mercedes", "00:55:01"));
////registros.add(new Tiempo("Kiri", "00:45:20"));
////registros.add(new Tiempo("Key", "00:56:12"));
////registros.add(new Tiempo("CarlosM", "00:39:34"));
//
//// Nuevos participantes
//List<Tiempo> nuevosParticipantes = new ArrayList<>();
//
//nuevosParticipantes.add(new Tiempo("Juan", "00:30:00"));
//nuevosParticipantes.add(new Tiempo("Pedro", "00:40:00"));
//nuevosParticipantes.add(new Tiempo("Luis", "00:27:00"));
////nuevosParticipantes.add(new Tiempo("Carlos", "00:35:30"));
////nuevosParticipantes.add(new Tiempo("Hector", "00:50:34"));
////nuevosParticipantes.add(new Tiempo("Karen", "00:45:20"));
//nuevosParticipantes.add(new Tiempo("Romina", "00:55:01"));
//nuevosParticipantes.add(new Tiempo("Fernan", "00:45:20"));
//nuevosParticipantes.add(new Tiempo("Carola", "00:56:12"));
//nuevosParticipantes.add(new Tiempo("Rei", "00:39:34"));
//
//
//for (Tiempo nuevoParticipante : nuevosParticipantes) {
//    boolean encontrado = false;
//    
//    for (Tiempo registro : registros) {
//    	
//        if (registro.getNombre().equals(nuevoParticipante.getNombre())) {
//        	
//            encontrado = true;
//            if (nuevoParticipante.getSegundos() < registro.getSegundos()) {
//            	
//                System.out.println(nuevoParticipante.getNombre() + " ha roto su récord! Nuevo tiempo: " + nuevoParticipante.getTiempo()+" Anterior: "+registro.getTiempo());
//                //registro.segundos = nuevoParticipante.getSegundos(); // SETEAR DATOS EN LISTA
//                 registro.setNewRecord(nuevoParticipante.getNombre() ,nuevoParticipante.getSegundos());
//                 System.out.println(nuevoParticipante.getNombre()+" "+nuevoParticipante.getTiempo());
//               
//            } else {
//                System.out.println(nuevoParticipante.getNombre() + " no ha roto su récord. Mejor tiempo: " + registro.getTiempo());
//            }
//            break;
//        }
//    }
//    if (!encontrado) {
//        registros.add(nuevoParticipante);
//        System.out.println(nuevoParticipante.getNombre() + " es un nuevo participante!");
//    }
//}
//
//// Ordenar registros
////Collections.sort(registros, Comparator.comparingLong(Tiempo::getSegundos).reversed()); //DE MAYOR A MENOR  
//Collections.sort(registros, Comparator.comparingLong(Tiempo::getSegundos)); // DE MENOR A MAYOR
//
//System.out.println("SIZE: "+registros.size());
//// Verificar si el tiempo es demasiado largo para entrar al top
//
//
//
//
//if(registros.size() >= 10) {
//    Tiempo ultimoRegistro = registros.get(registros.size() - 1); //ULTIMO USUARIO DEL REGISTRO
//    Tiempo utlimolugardeltop = registros.get(9);//POSICION DEL TOP
//    if (ultimoRegistro.getSegundos() > utlimolugardeltop.getSegundos()) {
//        System.out.println("El tiempo de " + ultimoRegistro.getNombre() + " (" + ultimoRegistro.getTiempo() + ") es demasiado largo para entrar al top. El tercer mejor tiempo es de: "+utlimolugardeltop.getNombre()+" : " + utlimolugardeltop.getTiempo());
//    }
//}

//================================================
//public class Console {
//		
//	
//	
//
//	
//	
//	
//	public static void setRecord() {
//		
//		
//		
//		List<Tiempo> registros = new ArrayList<>();
//        registros.add(new Tiempo("Luis", "00:25:00"));
//        registros.add(new Tiempo("Maria", "00:32:43"));
//        registros.add(new Tiempo("Marcos", "00:27:34"));
//
//        // Nuevos participantes
//        List<Tiempo> nuevosParticipantes = new ArrayList<>();
//       
//        nuevosParticipantes.add(new Tiempo("Juan", "00:30:00"));
//        nuevosParticipantes.add(new Tiempo("Pedro", "00:40:00"));
//        nuevosParticipantes.add(new Tiempo("Luis", "00:24:00"));
//
//        for (Tiempo nuevoParticipante : nuevosParticipantes) {
//            boolean encontrado = false;
//            
//            for (Tiempo registro : registros) {
//            	
//                if (registro.getNombre().equals(nuevoParticipante.getNombre())) {
//                	
//                    encontrado = true;
//                    if (nuevoParticipante.getSegundos() < registro.getSegundos()) {
//                    	
//                        System.out.println(nuevoParticipante.getNombre() + " ha roto su récord! Nuevo tiempo: " + nuevoParticipante.getTiempo());
//                        //registro.segundos = nuevoParticipante.getSegundos(); // SETEAR DATOS EN LISTA
//                         registro.setNewRecord(nuevoParticipante.getNombre() ,nuevoParticipante.getSegundos());
//                         System.out.println(nuevoParticipante.getNombre()+" "+nuevoParticipante.getTiempo());
//                       
//                    } else {
//                        System.out.println(nuevoParticipante.getNombre() + " no ha roto su récord. Mejor tiempo: " + registro.getTiempo());
//                    }
//                    break;
//                }
//            }
//            if (!encontrado) {
//                registros.add(nuevoParticipante);
//                System.out.println(nuevoParticipante.getNombre() + " es un nuevo participante!");
//            }
//        }
//
//        // Ordenar registros
//       //Collections.sort(registros, Comparator.comparingLong(Tiempo::getSegundos).reversed()); //DE MAYOR A MENOR  
//        Collections.sort(registros, Comparator.comparingLong(Tiempo::getSegundos)); // DE MENOR A MAYOR
//
//        // Verificar si el tiempo es demasiado largo para entrar al top
//        if (registros.size() > 4) {// ANTES ESTABA EN 3
//            Tiempo ultimoRegistro = registros.get(registros.size() - 1); //ULTIMO USUARIO DEL REGISTRO
//            Tiempo tercerMejorTiempo = registros.get(2);//POSICION DEL TOP
//            if (ultimoRegistro.getSegundos() > tercerMejorTiempo.getSegundos()) {
//                System.out.println("El tiempo de " + ultimoRegistro.getNombre() + " (" + ultimoRegistro.getTiempo() + ") es demasiado largo para entrar al top. El tercer mejor tiempo es " + tercerMejorTiempo.getTiempo());
//            }
//        }
//
//        // Mostrar top 3
//        System.out.println("Top 10:");
//        for (int i = 0; i < Math.min(10, registros.size()); i++) {
//            Tiempo registro = registros.get(i);
//            System.out.println((i + 1) + ". " + registro.getNombre() + " - " + registro.getTiempo());
//        }
//    }
