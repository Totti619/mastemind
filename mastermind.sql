-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 17-06-2017 a las 01:42:12
-- Versión del servidor: 10.1.10-MariaDB
-- Versión de PHP: 5.6.15

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
  `id_partida` int(10) UNSIGNED NOT NULL,
  `numero_aleatorio` varchar(5) NOT NULL,
  `nick` varchar(50) NOT NULL,
  `fecha` datetime NOT NULL,
  `cantidad_maxima_tiradas` int(11) NOT NULL,
  `partida_acabada` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `partidas`
--

INSERT INTO `partidas` (`id_partida`, `numero_aleatorio`, `nick`, `fecha`, `cantidad_maxima_tiradas`, `partida_acabada`) VALUES
(6, '32268', 'null', '2017-06-09 11:30:33', 0, 0),
(7, '07051', 'null', '2017-06-09 11:31:04', 0, 0),
(8, '82064', 'null', '2017-06-09 11:32:05', 0, 0),
(9, '90356', 'null', '2017-06-09 11:34:25', 0, 0),
(10, '42195', 'null', '2017-06-09 11:35:53', 0, 0),
(11, '95631', 'null', '2017-06-09 11:37:40', 0, 0),
(12, '49967', 'Anonymous', '2017-06-16 23:07:15', 0, 0),
(13, '52729', 'Anonymous', '2017-06-16 23:14:23', 10, 0),
(14, '19125', 'Anonymous', '2017-06-16 23:21:22', 0, 0),
(15, '12402', 'Anonymous', '2017-06-16 23:24:13', 10, 0),
(16, '91253', 'Anonymous', '2017-06-16 23:25:41', 10, 0),
(17, '17299', 'Anonymous', '2017-06-16 23:30:50', 0, 0),
(18, '87881', 'Anonymous', '2017-06-16 23:30:52', 10, 0),
(19, '02817', 'Anonymous', '2017-06-16 23:30:54', 0, 0),
(20, '49104', 'Anonymous', '2017-06-16 23:37:27', 0, 0),
(21, '59727', 'Anonymous', '2017-06-16 23:37:29', 10, 0),
(22, '44255', 'Anonymous', '2017-06-16 23:38:16', 0, 0),
(23, '95112', 'Anonymous', '2017-06-16 23:38:17', 10, 0),
(24, '10619', 'Anonymous', '2017-06-16 23:57:20', 0, 0),
(25, '08529', 'Anonymous', '2017-06-17 01:17:30', 0, 0),
(26, '29672', 'null', '2017-06-09 11:30:33', 0, 0),
(27, '12860', 'null', '2017-06-09 11:30:33', 0, 0),
(28, '20813', 'null', '2017-06-09 11:31:04', 0, 0),
(29, '40215', 'null', '2017-06-09 11:31:04', 0, 0),
(30, '49798', 'Anonymous', '2017-06-17 01:27:42', 0, 0),
(31, '26246', 'null', '2017-06-09 11:30:33', 0, 0),
(32, '95874', 'null', '2017-06-09 11:30:33', 0, 0),
(33, '31441', 'null', '2017-06-09 11:32:05', 0, 0),
(34, '30483', 'null', '2017-06-09 11:32:05', 0, 0),
(35, '80334', 'null', '2017-06-09 11:31:04', 0, 0),
(36, '20225', 'null', '2017-06-09 11:31:04', 0, 0),
(37, '84721', 'null', '2017-06-09 11:32:05', 0, 0),
(38, '81435', 'null', '2017-06-09 11:32:05', 0, 0),
(39, '26796', 'null', '2017-06-09 11:34:25', 0, 0),
(40, '36072', 'null', '2017-06-09 11:34:25', 0, 0),
(41, '54577', 'null', '2017-06-09 11:35:53', 0, 0),
(42, '64460', 'null', '2017-06-09 11:35:53', 0, 0),
(43, '89782', 'null', '2017-06-09 11:37:40', 0, 0),
(44, '41809', 'null', '2017-06-09 11:37:40', 0, 0),
(45, '79659', 'Anonymous', '2017-06-16 23:21:22', 0, 0),
(46, '92307', 'Anonymous', '2017-06-16 23:21:22', 0, 0),
(47, '13647', 'Anonymous', '2017-06-16 23:30:50', 0, 0),
(48, '72952', 'Anonymous', '2017-06-16 23:30:50', 0, 0),
(49, '73537', 'Anonymous', '2017-06-16 23:21:22', 0, 0),
(50, '71240', 'Anonymous', '2017-06-16 23:21:22', 0, 0),
(51, '49414', 'null', '2017-06-09 11:37:40', 0, 0),
(52, '30305', 'null', '2017-06-09 11:37:40', 0, 0),
(53, '61981', 'Anonymous', '2017-06-16 23:21:22', 0, 0),
(54, '84647', 'Anonymous', '2017-06-16 23:21:22', 0, 0),
(55, '17366', 'Anonymous', '2017-06-16 23:30:50', 0, 0),
(56, '65284', 'Anonymous', '2017-06-16 23:30:50', 0, 0),
(57, '54801', 'null', '2017-06-09 11:31:04', 0, 0),
(58, '86213', 'null', '2017-06-09 11:31:04', 0, 0),
(59, '21172', 'null', '2017-06-09 11:32:05', 0, 0),
(60, '18054', 'null', '2017-06-09 11:32:05', 0, 0),
(61, '09740', 'null', '2017-06-09 11:32:05', 0, 0),
(62, '07636', 'null', '2017-06-09 11:32:05', 0, 0),
(63, '53764', 'null', '2017-06-09 11:30:33', 0, 0),
(64, '71589', 'null', '2017-06-09 11:30:33', 0, 0),
(65, '93732', 'Anonymous', '2017-06-17 01:27:42', 0, 0),
(66, '22761', 'Anonymous', '2017-06-17 01:27:42', 0, 0),
(67, '24971', 'null', '2017-06-09 11:31:04', 0, 0),
(68, '62466', 'null', '2017-06-09 11:31:04', 0, 0),
(69, '00208', 'null', '2017-06-09 11:31:04', 0, 0),
(70, '93721', 'null', '2017-06-09 11:31:04', 0, 0),
(71, '56464', 'Anonymous', '2017-06-17 01:27:42', 0, 0),
(72, '50374', 'Anonymous', '2017-06-17 01:27:42', 0, 0),
(73, '60410', 'null', '2017-06-09 11:32:05', 0, 0),
(74, '50403', 'null', '2017-06-09 11:32:05', 0, 0),
(75, '92772', 'Anonymous', '2017-06-16 23:38:17', 10, 0),
(76, '37466', 'Anonymous', '2017-06-16 23:38:17', 10, 0),
(77, '75457', 'Anonymous', '2017-06-16 23:38:17', 10, 0),
(78, '46714', 'Anonymous', '2017-06-16 23:38:17', 10, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiradas`
--

CREATE TABLE `tiradas` (
  `id_tirada` int(10) UNSIGNED NOT NULL,
  `id_partida` int(10) UNSIGNED NOT NULL,
  `numero_entrado` varchar(5) NOT NULL,
  `mal_posicionados` varchar(5) NOT NULL,
  `bien_posicionados` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiradas`
--

INSERT INTO `tiradas` (`id_tirada`, `id_partida`, `numero_entrado`, `mal_posicionados`, `bien_posicionados`) VALUES
(1, 10, '00002', '00001', '00000'),
(2, 11, '12345', '10101', '00000'),
(3, 11, '31500', '11100', '00000'),
(4, 11, '51030', '11000', '00010'),
(5, 11, '15030', '10000', '01010'),
(6, 11, '05130', '00100', '01010'),
(7, 11, '05031', '00000', '01011'),
(8, 11, '25231', '00000', '01011'),
(9, 11, '25431', '00000', '01011'),
(10, 11, '25631', '00000', '01111'),
(11, 11, '75631', '00000', '01111'),
(12, 11, '95631', '00000', '11111'),
(13, 16, '01021', '00011', '01000'),
(14, 16, '01021', '00011', '01000'),
(15, 19, '00123', '01110', '10000'),
(16, 19, '12345', '10000', '01000'),
(17, 23, '00012', '00000', '00011'),
(18, 23, '00012', '00000', '00011'),
(19, 25, '01122', '00001', '10010'),
(20, 25, '01122', '00001', '10010'),
(21, 25, '01122', '00001', '10010'),
(22, 25, '01122', '00001', '10010'),
(23, 25, '01122', '00001', '10010'),
(24, 25, '01122', '00001', '10010'),
(25, 25, '01122', '00001', '10010'),
(26, 25, '01122', '00001', '10010'),
(27, 25, '01122', '00001', '10010'),
(28, 25, '01122', '00001', '10010');

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
  ADD KEY `fk_partida` (`id_partida`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `partidas`
--
ALTER TABLE `partidas`
  MODIFY `id_partida` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;
--
-- AUTO_INCREMENT de la tabla `tiradas`
--
ALTER TABLE `tiradas`
  MODIFY `id_tirada` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tiradas`
--
ALTER TABLE `tiradas`
  ADD CONSTRAINT `fk_partida` FOREIGN KEY (`id_partida`) REFERENCES `partidas` (`id_partida`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
