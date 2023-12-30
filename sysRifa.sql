create DATABASE SysRifa;

use SysRifa;

create TABLE Apostador(
	codigo_Apostador int  primary key auto_increment,
	nome VARCHAR(30)  unique,
	localidade VARCHAR(150),
	whatsapp VARCHAR(20),
	email VARCHAR(40) 
);

create Table Rifa(
	codigo_Rifa int primary key auto_increment,
	dataCriacao TIMESTAMP,
	dataSorteio TIMESTAMP,
	valorAposta float,
	quantApostas int,
	status varchar(10)
	
);

create Table Premio(
	codigo_premio int  primary key auto_increment,
	descricao VARCHAR(250) ,
	cod_fk_Rifa int, 
	
	FOREIGN key (Cod_fk_Rifa) REFERENCES Rifa(codigo_Rifa)
	
);

create Table Aposta(
	codigo_Aposta int  primary key auto_increment,
	numero int,
	pago BOOLEAN,
	vencedor BOOLEAN,
	Cod_fk_apostador int,
	Cod_fk_Rifa int,
	
	FOREIGN key (Cod_fk_apostador) REFERENCES Apostador(codigo_Apostador),
	FOREIGN key (Cod_fk_Rifa) REFERENCES Rifa(codigo_Rifa)
);


	