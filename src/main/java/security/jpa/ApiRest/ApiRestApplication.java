package security.jpa.ApiRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import security.jpa.ApiRest.model.*;
import security.jpa.ApiRest.repository.*;

@SpringBootApplication
public class ApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

	@Autowired
	private CatalogCaptureRepository catalogCaptureRepository;

	@Autowired
	private SubCatalogCaptureRepository subCatalogCaptureRepository;


/*
		@Bean
        CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder, CatalogsRepository catalogsRepository,
                                            CaptureRepository captureRepository, CatalogRepository catalogRepository,
										CatalogCaptureRepository catalogCaptureRepository, SubCatalogCaptureRepository subCatalogCaptureRepository
		){

			return args -> {



			  catalogsRepository.saveAndFlush(new CatalogRolesUsersModel("Administrador", "1"));
				catalogsRepository.saveAndFlush(new CatalogRolesUsersModel("Captura", "1"));
				catalogsRepository.saveAndFlush(new CatalogRolesUsersModel("Consulta", "1"));
				//catalogsRepository.save(new CatalogRolesUsersModel("Role_User", (Short) 10));

				users.saveAndFlush(new UserModel("User", encoder.encode("password"), (byte) 1, "Admin", false));
				//users.saveAndFlush(new UserModel("Capture", encoder.encode("password"), (byte) 2, "paty", false));
				//users.saveAndFlush(new UserModel("Consulta", encoder.encode("password"), (byte) 3, "solana", false));




				catalogRepository.save(new CatalogsModel("Usuarios"));
				catalogRepository.save(new CatalogsModel("Captura"));
				catalogRepository.save(new CatalogsModel("Reportes"));


				// 1. Primero creas y guardas el catálogo Padre
				CatalogCaptureModel addCatalog1 = new CatalogCaptureModel((short)2, "Tipo de solicitud");
				CatalogCaptureModel addCatalog2 = new CatalogCaptureModel((short)2, "Status de solicitud");
				CatalogCaptureModel addCatalog3 = new CatalogCaptureModel((short)2, "Bolsas");
				CatalogCaptureModel addCatalog4 = new CatalogCaptureModel((short)2, "Fondo");
				CatalogCaptureModel addCatalog5 = new CatalogCaptureModel((short)2, "Ures");

				addCatalog1 = catalogCaptureRepository.saveAndFlush(addCatalog1); // Guardamos para que genere su ID
				addCatalog2 = catalogCaptureRepository.saveAndFlush(addCatalog2);
				addCatalog3 = catalogCaptureRepository.saveAndFlush(addCatalog3);
				addCatalog4 = catalogCaptureRepository.saveAndFlush(addCatalog4);
				addCatalog5 = catalogCaptureRepository.saveAndFlush(addCatalog5);

				// 2. Ahora sí, pasas el OBJETO "miCatalogo" como parámetro al hijo
				SubCatalogCaptureModel sub1 = new SubCatalogCaptureModel(addCatalog1, "1", "ORDEN DE COMPRA");
				SubCatalogCaptureModel sub2 = new SubCatalogCaptureModel(addCatalog1, "2", "RECIBO");
				SubCatalogCaptureModel sub3 = new SubCatalogCaptureModel(addCatalog1, "3", "REPOSICIÓN");
				SubCatalogCaptureModel sub4 = new SubCatalogCaptureModel(addCatalog1, "4", "VALE");
				SubCatalogCaptureModel sub5 = new SubCatalogCaptureModel(addCatalog1, "5", "COMPRA SUB");
				SubCatalogCaptureModel sub6 = new SubCatalogCaptureModel(addCatalog1, "6", "BECA");
				SubCatalogCaptureModel sub7 = new SubCatalogCaptureModel(addCatalog1, "7", "PARCIAL");
				SubCatalogCaptureModel sub8 = new SubCatalogCaptureModel(addCatalog1, "8", "ANTICIPO");
				SubCatalogCaptureModel sub9 = new SubCatalogCaptureModel(addCatalog2, "1", "PAGADA");
				SubCatalogCaptureModel sub10 = new SubCatalogCaptureModel(addCatalog2, "2", "TRÁMITE DE PAGO");
				SubCatalogCaptureModel sub12 = new SubCatalogCaptureModel(addCatalog2, "3", "COMPROBADA");
				SubCatalogCaptureModel sub13 = new SubCatalogCaptureModel(addCatalog3,"33332289","SUBSIDIO ORDINARIO GOBIERNO FEDERAL 2026 CUCI");
				SubCatalogCaptureModel sub14 = new SubCatalogCaptureModel(addCatalog3,"33333175","INGRESOS AUTOGENERADOS 2026 CUCI");
				SubCatalogCaptureModel sub15 = new SubCatalogCaptureModel(addCatalog3,"33419551","FIDEICOMISO PARA EL FONDO DE LA INFRAESTRUCTURA FISICA DE LA RED UNIVERSITARIA FIFRU EJER ANT 2026 CUCI (25)");
				SubCatalogCaptureModel sub16 = new SubCatalogCaptureModel(addCatalog3,"33421495","FIDEICOMISO PARA EL FONDO DE LA INFRAESTRUCTURA FISICA DE LA RED UNIVERSITARIA FIFRU EJER ANT 2026 CUCI (16)");
				SubCatalogCaptureModel sub17 = new SubCatalogCaptureModel(addCatalog3,"33472192","INGRESOS AUTOGENERADOS EJERCICIOS ANTERIORES 2026 CUCI");
				SubCatalogCaptureModel sub18 = new SubCatalogCaptureModel(addCatalog3,"34286166","SUBSIDIO ORDINARIO GOBIERNO ESTATAL 2026 CUCI");
				SubCatalogCaptureModel sub19 = new SubCatalogCaptureModel(addCatalog3,"34692150","PROGRAMA APOYO A MEJORA EN CONDICIONES D PRODUCCIÓN D PERSONAS INTEGRANTES DL SNII Y SNCA (PROSNII) 2026 CUCI");
				SubCatalogCaptureModel sub20 = new SubCatalogCaptureModel(addCatalog3,"33333492","SUBSIDIO ORDINARIO GOBIERNO ESTATAL 2026 CUCI");
				SubCatalogCaptureModel sub21 = new SubCatalogCaptureModel(addCatalog3,"33989261","EXAMEN DE ADMISION 2026 CUCI");
				SubCatalogCaptureModel sub22 = new SubCatalogCaptureModel(addCatalog3,"34019166","ADQUISICION MATERIAL BIBLIOGRAFICO (ACERVO) EQUIPAMIENTO PARA BIBLIOTECAS 2026 CUCI");
				SubCatalogCaptureModel sub23 = new SubCatalogCaptureModel(addCatalog3,"34270153","PROGRAMA PARA LA CONSOLIDACIÓN DEL POSGRADO (PCP) 2026 CUCI");
				SubCatalogCaptureModel sub24 = new SubCatalogCaptureModel(addCatalog3,"34315771","CONCURRENCIAS FINANCIERAS PARA LA INVESTIGACIÓN Y ATENCIÓN DE LA VINCULACIÓN 2026 CUCI");
				SubCatalogCaptureModel sub25 = new SubCatalogCaptureModel(addCatalog3,"34322183","PROGRAMA APOYO A MEJORA EN CONDICIONES D PRODUCCIÓN D PERSONAS INTEGRANTES DL SNII Y SNCA (PROSNII) 2026 CUCI");
				SubCatalogCaptureModel sub26 = new SubCatalogCaptureModel(addCatalog3,"34322974","PROGRAMAS DE FORMACION DOCENTE 2026 CUCI");
				SubCatalogCaptureModel sub27 = new SubCatalogCaptureModel(addCatalog3,"34362937","PROG COMPENSA PARA LA TRANSICIÓN GRADUAL HACIA LA GRATUIDAD DE LOS SERVICIOS EDUCATIVOS DE POSGRADO 2026 CUCI");
				SubCatalogCaptureModel sub28 = new SubCatalogCaptureModel(addCatalog3,"34487608","FIDEICOMISO PARA EL FONDO DE LA INFRAESTRUCTURA FISICA DE LA RED UNIVERSITARIA FIFRU 2026 CUCI (27)");
				SubCatalogCaptureModel sub29 = new SubCatalogCaptureModel(addCatalog3,"34692815","PROGRAMA DE FORTALECIMIENTO DE INSTITUTOS, CENTROS Y LABORATORIOS DE INVESTIGACIÓN 2026 CUCI");
				SubCatalogCaptureModel sub30 = new SubCatalogCaptureModel(addCatalog4,"1.1.1.1","SUBSIDIO ORDINARIO GOBIERNO FEDERAL");
				SubCatalogCaptureModel sub31 = new SubCatalogCaptureModel(addCatalog4,"1.1.2","INGRESOS AUTOGENERADOS");
				SubCatalogCaptureModel sub32 = new SubCatalogCaptureModel(addCatalog4,"1.3.13.3","FIDEICOMISO DEL FONDO PARA LA INFRAESTRUCTURA FÍSICA DE LA RED UNIVERSITARIA FIFRU EJER ANT");
				SubCatalogCaptureModel sub33 = new SubCatalogCaptureModel(addCatalog4,"1.3.2","INGRESOS AUTOGENERADOS EJERCICIOS ANTERIORES");
				SubCatalogCaptureModel sub34 = new SubCatalogCaptureModel(addCatalog4,"1.1.1.3","SUBSIDIO ORDINARIO GOBIERNO ESTATAL");
				SubCatalogCaptureModel sub35 = new SubCatalogCaptureModel(addCatalog4,"1.1.4.8.15","PROGRAMA APOYO A MEJORA EN CONDICIONES D PRODUCCIÓN D PERSONAS INTEGRANTES DL SNII Y SNCA (PROSNII)");
				SubCatalogCaptureModel sub36 = new SubCatalogCaptureModel(addCatalog4,"1.1.6.43","EXAMEN DE ADMISION");
				SubCatalogCaptureModel sub37 = new SubCatalogCaptureModel(addCatalog4,"1.1.4.2","ADQUISICION MATERIAL BIBLIOGRAFICO (ACERVO) EQUIPAMIENTO PARA BIBLIOTECAS");
				SubCatalogCaptureModel sub38 = new SubCatalogCaptureModel(addCatalog4,"1.1.4.8.20","PROGRAMA PARA LA CONSOLIDACIÓN DEL POSGRADO (PCP)");
				SubCatalogCaptureModel sub39 = new SubCatalogCaptureModel(addCatalog4,"1.1.6.64","CONCURRENCIAS FINANCIERAS PARA LA INVESTIGACIÓN Y ATENCIÓN DE LA VINCULACIÓN");
				SubCatalogCaptureModel sub41 = new SubCatalogCaptureModel(addCatalog4,"1.1.9.16","PROGRAMAS DE FORMACION DOCENTE");
				SubCatalogCaptureModel sub42 = new SubCatalogCaptureModel(addCatalog4,"1.1.4.8.18","PROG COMPENSA PARA LA TRANSICIÓN GRADUAL HACIA LA GRATUIDAD DE LOS SERVICIOS EDUCATIVOS DE POSGRADO");
				SubCatalogCaptureModel sub43 = new SubCatalogCaptureModel(addCatalog4,"1.2.1.30","FIDEICOMISO DEL FONDO PARA LA INFRAESTRUCTURA FISICA DE LA RED UNIVERSITARIA");
				SubCatalogCaptureModel sub44 = new SubCatalogCaptureModel(addCatalog4,"1.1.4.8.12","PROGRAMA DE FORTALECIMIENTO DE INSTITUTOS, CENTROS Y LABORATORIOS DE INVESTIGACIÓN");
				SubCatalogCaptureModel sub45 = new SubCatalogCaptureModel(addCatalog5,"3.2.1","RECTORIA");
				SubCatalogCaptureModel sub46 = new SubCatalogCaptureModel(addCatalog5,"3.2.2","SECRERARÍA ACADÉMICA");
				SubCatalogCaptureModel sub47 = new SubCatalogCaptureModel(addCatalog5,"3.2.2.2","COORDINACIÓN DE PLANEACIÓN");
				SubCatalogCaptureModel sub48 = new SubCatalogCaptureModel(addCatalog5,"3.2.2.3","COORDINACIÓN DE PROGRAMAS DOCENTES");
				SubCatalogCaptureModel sub49 = new SubCatalogCaptureModel(addCatalog5,"3.2.2.4","COORDINACIÓN DE INVESTIGACIÓN");
				SubCatalogCaptureModel sub50 = new SubCatalogCaptureModel(addCatalog5,"3.2.2.5","COORDINACIÓN DE EXTENSIÓN");
				SubCatalogCaptureModel sub51 = new SubCatalogCaptureModel(addCatalog5,"3.2.2.6","COORDINACIÓN DE SERVICIOS ACADÉMICOS");
				SubCatalogCaptureModel sub52 = new SubCatalogCaptureModel(addCatalog5,"3.2.2.7","COORDINACIÓN DE TECNOLOGÍAS PARA EL APRENDIZAJE (CTA)");
				SubCatalogCaptureModel sub53 = new SubCatalogCaptureModel(addCatalog5,"3.2.3","SECRETARÍA ADMINISTRATIVA");
				SubCatalogCaptureModel sub54 = new SubCatalogCaptureModel(addCatalog5,"3.2.4","DIVISIÓN DE DESARROLLO BIOTECNOLÓGICO");
				SubCatalogCaptureModel sub55 = new SubCatalogCaptureModel(addCatalog5,"3.2.4.2","DEPARTAMENTO DE CIENCIAS TECNOLÓGICAS");
				SubCatalogCaptureModel sub56 = new SubCatalogCaptureModel(addCatalog5,"3.2.4.3","DEPARAMENTO DE CIENCIAS MÉDICAS Y DE LA VIDA");
				SubCatalogCaptureModel sub57 = new SubCatalogCaptureModel(addCatalog5,"3.2.4.4","DEPARTAMENTO DE CIENCIAS BÁSICAS");
				SubCatalogCaptureModel sub58 = new SubCatalogCaptureModel(addCatalog5,"3.2.5","DIVISIÓN DE CIENCIAS ECONÓMICAS ADMINISTRATIVAS");
				SubCatalogCaptureModel sub59 = new SubCatalogCaptureModel(addCatalog5,"3.2.5.2","DEPARTAMENTO DE NEGOCIOS");
				SubCatalogCaptureModel sub60 = new SubCatalogCaptureModel(addCatalog5,"3.2.5.3","DEPARTAMENTO DE ESTUDIOS ECONÓMICOS E INTERNACIONALES");
				SubCatalogCaptureModel sub61 = new SubCatalogCaptureModel(addCatalog5,"3.2.5.4","DEPARTAMENTO DE CONTADURÍA Y FINANZAS");
				SubCatalogCaptureModel sub62 = new SubCatalogCaptureModel(addCatalog5,"3.2.6","DIVISIÓN DE ESTUDIOS JURÍDICOS Y SOCIALES");
				SubCatalogCaptureModel sub63 = new SubCatalogCaptureModel(addCatalog5,"3.2.6.2","DEPARTAMENTO DE JUSTICIA Y DERECHO");
				SubCatalogCaptureModel sub64 = new SubCatalogCaptureModel(addCatalog5,"3.2.6.3","DEPARTAMENTO DE POLÍTICA Y SOCIEDAD");
				SubCatalogCaptureModel sub65 = new SubCatalogCaptureModel(addCatalog5,"3.2.6.4","DEPARTAMENTO DE COMUNICACIÓN Y PSICOLOGÍA");



				subCatalogCaptureRepository.saveAndFlush(sub1);
				subCatalogCaptureRepository.saveAndFlush(sub2);
				subCatalogCaptureRepository.saveAndFlush(sub3);
				subCatalogCaptureRepository.saveAndFlush(sub4);
				subCatalogCaptureRepository.saveAndFlush(sub5);
				subCatalogCaptureRepository.saveAndFlush(sub6);
				subCatalogCaptureRepository.saveAndFlush(sub7);
				subCatalogCaptureRepository.saveAndFlush(sub8);
				subCatalogCaptureRepository.saveAndFlush(sub9);
				subCatalogCaptureRepository.saveAndFlush(sub10);
				subCatalogCaptureRepository.saveAndFlush(sub12);
				subCatalogCaptureRepository.saveAndFlush(sub13);
				subCatalogCaptureRepository.saveAndFlush(sub14);
				subCatalogCaptureRepository.saveAndFlush(sub15);
				subCatalogCaptureRepository.saveAndFlush(sub16);
				subCatalogCaptureRepository.saveAndFlush(sub17);
				subCatalogCaptureRepository.saveAndFlush(sub18);
				subCatalogCaptureRepository.saveAndFlush(sub19);
				subCatalogCaptureRepository.saveAndFlush(sub20);
				subCatalogCaptureRepository.saveAndFlush(sub21);
				subCatalogCaptureRepository.saveAndFlush(sub22);
				subCatalogCaptureRepository.saveAndFlush(sub23);
				subCatalogCaptureRepository.saveAndFlush(sub24);
				subCatalogCaptureRepository.saveAndFlush(sub25);
				subCatalogCaptureRepository.saveAndFlush(sub26);
				subCatalogCaptureRepository.saveAndFlush(sub27);
				subCatalogCaptureRepository.saveAndFlush(sub28);
				subCatalogCaptureRepository.saveAndFlush(sub29);
				subCatalogCaptureRepository.saveAndFlush(sub30);
				subCatalogCaptureRepository.saveAndFlush(sub31);
				subCatalogCaptureRepository.saveAndFlush(sub32);
				subCatalogCaptureRepository.saveAndFlush(sub33);
				subCatalogCaptureRepository.saveAndFlush(sub34);
				subCatalogCaptureRepository.saveAndFlush(sub35);
				subCatalogCaptureRepository.saveAndFlush(sub36);
				subCatalogCaptureRepository.saveAndFlush(sub37);
				subCatalogCaptureRepository.saveAndFlush(sub38);
				subCatalogCaptureRepository.saveAndFlush(sub39);
				//subCatalogCaptureRepository.saveAndFlush(sub40);
				subCatalogCaptureRepository.saveAndFlush(sub41);
				subCatalogCaptureRepository.saveAndFlush(sub42);
				subCatalogCaptureRepository.saveAndFlush(sub43);
				subCatalogCaptureRepository.saveAndFlush(sub44);
				subCatalogCaptureRepository.saveAndFlush(sub45);
				subCatalogCaptureRepository.saveAndFlush(sub46);
				subCatalogCaptureRepository.saveAndFlush(sub47);
				subCatalogCaptureRepository.saveAndFlush(sub48);
				subCatalogCaptureRepository.saveAndFlush(sub49);
				subCatalogCaptureRepository.saveAndFlush(sub50);
				subCatalogCaptureRepository.saveAndFlush(sub51);
				subCatalogCaptureRepository.saveAndFlush(sub52);
				subCatalogCaptureRepository.saveAndFlush(sub53);
				subCatalogCaptureRepository.saveAndFlush(sub54);
				subCatalogCaptureRepository.saveAndFlush(sub55);
				subCatalogCaptureRepository.saveAndFlush(sub56);
				subCatalogCaptureRepository.saveAndFlush(sub57);
				subCatalogCaptureRepository.saveAndFlush(sub58);
				subCatalogCaptureRepository.saveAndFlush(sub59);
				subCatalogCaptureRepository.saveAndFlush(sub60);
				subCatalogCaptureRepository.saveAndFlush(sub61);
				subCatalogCaptureRepository.saveAndFlush(sub62);
				subCatalogCaptureRepository.saveAndFlush(sub63);
				subCatalogCaptureRepository.saveAndFlush(sub64);
				subCatalogCaptureRepository.saveAndFlush(sub65);

				captureRepository.save(new CaptureModel(
						"2026-09-01",
						(byte)1, // user create application
						(byte)1, // user last update aplication
						100,
						(short) 1, //Typeapplication
						(short) 1, //StatusApplication,
						"",
						false,
						false,
						false,
						"Aplication concept",
						"beneficiaryName",
						"paymentMethod",
						"2.1.3", // project
						(short)30, //"fundApplication",
						(short)1, // stockNumber numero de bolsa
						(short)30, //numberUre
						0, // numberDepcon
						"", // depcon name
						(float)1,
						(float)1,
						(float)1,
						0,
						"name package",
						(byte)0, // deleted
						(byte)1 // user deleted
				));

			};

			};
*/

		}






