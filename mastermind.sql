-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 20-06-2017 a las 15:53:25
-- Versión del servidor: 10.1.19-MariaDB
-- Versión de PHP: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mastermind`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partidas`
--

CREATE TABLE `partidas` (
  `id_partida` int(11) NOT NULL,
  `numero_aleatorio` varchar(5) NOT NULL,
  `nick` varchar(254) NOT NULL,
  `fecha` datetime NOT NULL,
  `cantidad_maxima_tiradas` int(11) NOT NULL,
  `partida_acabada` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `partidas`
--

INSERT INTO `partidas` (`id_partida`, `numero_aleatorio`, `nick`, `fecha`, `cantidad_maxima_tiradas`, `partida_acabada`) VALUES
(18, '33710', 'Anonymous', '2017-06-19 17:42:42', 0, 0),
(19, '77806', 'Anonymous', '2017-06-19 17:42:48', 0, 0),
(20, '01959', 'Anonymous', '2017-06-19 17:44:16', 0, 0),
(21, '08701', 'Anonymous', '2017-06-19 17:44:25', 10, 0),
(22, '17038', 'Anonymous', '2017-06-19 17:46:03', 0, 0),
(23, '58180', 'Anonymous', '2017-06-19 17:47:00', 0, 0),
(24, '83541', 'Anonymous', '2017-06-19 17:47:02', 0, 0),
(25, '21299', 'Anonymous', '2017-06-19 17:47:11', 10, 0),
(26, '34933', 'Anonymous', '2017-06-19 17:47:12', 0, 0),
(27, '52601', 'Anonymous', '2017-06-19 17:47:13', 0, 0),
(28, '40870', 'Anonymous', '2017-06-19 17:47:15', 0, 0),
(29, '69158', 'Anonymous', '2017-06-19 17:47:18', 10, 0),
(30, '87664', 'Anonymous', '2017-06-19 17:48:55', 0, 0),
(31, '83013', 'Anonymous', '2017-06-19 17:48:56', 0, 0),
(32, '93488', 'Anonymous', '2017-06-19 17:48:59', 10, 0),
(33, '03142', 'Anonymous', '2017-06-19 17:50:39', 0, 0),
(34, '62933', 'Anonymous', '2017-06-19 17:50:41', 10, 0),
(35, '00105', 'Asdf', '2017-06-19 17:50:50', 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiradas`
--

CREATE TABLE `tiradas` (
  `id_tirada` int(11) NOT NULL,
  `id_partida` int(11) NOT NULL,
  `numero_entrado` varchar(5) NOT NULL,
  `mal_posicionados` varchar(5) NOT NULL,
  `bien_posicionados` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiradas`
--

INSERT INTO `tiradas` (`id_tirada`, `id_partida`, `numero_entrado`, `mal_posicionados`, `bien_posicionados`) VALUES
(108, 20, '00113', '01110', '10000'),
(109, 20, '00113', '01110', '10000'),
(110, 20, '00113', '01110', '10000'),
(111, 20, '00113', '01110', '10000'),
(112, 20, '00113', '01110', '10000'),
(113, 21, '00113', '01110', '10000'),
(114, 21, '00113', '01110', '10000'),
(115, 21, '00113', '01110', '10000'),
(116, 21, '00113', '01110', '10000'),
(117, 21, '00113', '01110', '10000'),
(118, 24, '00001', '00000', '00001'),
(119, 24, '00001', '00000', '00001'),
(120, 24, '00001', '00000', '00001'),
(121, 24, '00001', '00000', '00001'),
(122, 24, '00001', '00000', '00001'),
(123, 24, '00001', '00000', '00001'),
(124, 24, '00001', '00000', '00001'),
(125, 24, '00001', '00000', '00001'),
(126, 24, '00001', '00000', '00001');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `partidas`
--
ALTER TABLE `partidas`
  ADD PRIMARY KEY (`id_partida`);

--
-- Indices de la tabla `tiradas`
--
ALTER TABLE `tiradas`
  ADD PRIMARY KEY (`id_tirada`),
  ADD KEY `FK_partida` (`id_partida`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `partidas`
--
ALTER TABLE `partidas`
  MODIFY `id_partida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT de la tabla `tiradas`
--
ALTER TABLE `tiradas`
  MODIFY `id_tirada` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tiradas`
--
ALTER TABLE `tiradas`
  ADD CONSTRAINT `FK_partida` FOREIGN KEY (`id_partida`) REFERENCES `partidas` (`id_partida`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
