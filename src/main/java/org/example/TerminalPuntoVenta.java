import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Login {
    // Clase para el inicio de sesion
    private String dni;
    private LocalDate fechaNacimiento;

    public Login(String dni, LocalDate fechaNacimiento) {
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
}

class Carrito {
    // Clase para representar el carrito de compras
    private List<Producto> productos;

    public Carrito() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("Producto agregado al carrito: " + producto.getNombre());
    }

    public void eliminarProducto(int indice) {
        if (indice >= 0 && indice < productos.size()) {
            Producto productoEliminado = productos.remove(indice);
            System.out.println("Producto eliminado del carrito: " + productoEliminado.getNombre());
        } else {
            System.out.println("Indice invalido");
        }
    }

    public void mostrarCarrito() {
        System.out.println("Productos en el carrito:");
        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i).getNombre());
        }
    }

    public List<Producto> getProductos() {
        return productos;
    }
}

class Producto {
    // Clase para representar un producto
    private String nombre;
    private double precio;
    private CategoriaProducto categoria;

    public Producto(String nombre, double precio, CategoriaProducto categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }
}

class Ticket {
    // Clase para representar el ticket de compra
    private List<Producto> productos;

    public Ticket() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("Producto agregado al ticket: " + producto.getNombre());
    }

    public void generarTicket() {
        System.out.println("Ticket de compra:");
        for (Producto producto : productos) {
            System.out.println(producto.getNombre() + " - $" + producto.getPrecio());
        }
    }
}

enum CategoriaProducto {
    // Enumeracion para las categorias de los productos
    ELECTRONICA,
    ROPA,
    ALIMENTOS,
    HOGAR,
    OTROS
}

class Stock {
    private Map<Producto, Integer> inventario;

    public Stock() {
        inventario = new HashMap<>();
        List<Producto> productos = crearInventarioAleatorio();
        for (Producto producto : productos) {
            inventario.put(producto, 50); // Cantidad por defecto: 50 unidades
        }
    }

    public void mostrarInventario() {
        System.out.println("Inventario:");
        for (Map.Entry<Producto, Integer> entry : inventario.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println(producto.getNombre() + " - Cantidad: " + cantidad);
        }
    }

    public int obtenerCantidadDisponible(Producto producto) {
        Integer cantidad = inventario.get(producto);
        if (cantidad != null) {
            return cantidad;
        } else {
            return 0;
        }
    }

    public boolean actualizarStock(Producto producto, int cantidad) {
        Integer stockActual = inventario.get(producto);
        if (stockActual != null) {
            int stockNuevo = stockActual - cantidad;
            if (stockNuevo >= 0) {
                inventario.put(producto, stockNuevo);
                return true; // Stock actualizado con exito
            }
        }
        return false; // No hay suficiente stock disponible
    }

    public List<Producto> getInventario() {
        return new ArrayList<>(inventario.keySet());
    }

    private List<Producto> crearInventarioAleatorio() {
        List<String> nombres = Arrays.asList("Arroz", "Procesadora", "Fideos", "Agua", "Galletita");
        List<Producto> inventario = new ArrayList<>();
        Random random = new Random();

        for (String nombre : nombres) {
            double precio = random.nextDouble() * 100.0;
            CategoriaProducto categoria;

            if (nombre.equals("Fideos") || nombre.equals("Arroz") || nombre.equals("Agua") || nombre.equals("Galletita")) {
                categoria = CategoriaProducto.ALIMENTOS;
            } else if (nombre.equals("Procesadora")) {
                categoria = CategoriaProducto.ELECTRONICA;
            } else {
                categoria = CategoriaProducto.OTROS;
            }

            Producto producto = new Producto(nombre, precio, categoria);
            inventario.add(producto);
        }

        return inventario;
    }
}

public class TerminalPuntoVenta {
    public static void main(String[] args) {
        // Crear productos aleatorios
        Stock stock = new Stock();

        // Iniciar sesion
        Login login = new Login("41204198", LocalDate.of(1998, 5, 10));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Iniciar sesion");

        // Solicitar DNI valido
        String dni = "";
        boolean dniValido = false;
        while (!dniValido) {
            System.out.print("DNI: ");
            if (scanner.hasNext("\\d+")) {
                dni = scanner.next();
                scanner.nextLine(); // Salto de linea
                dniValido = true;
            } else {
                System.out.println("DNI invalido. Debe contener solo numeros.");
                System.out.print("Ingrese el DNI nuevamente: ");
                scanner.nextLine(); // Descartar entrada invalida
            }
        }


        // Solicitar fecha de nacimiento valida
        LocalDate fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.print("Fecha de nacimiento (Formato: dd/MM/aaaa): ");
            String fechaNacimientoStr = scanner.nextLine();

            // Verificar si la fecha contiene solo numeros
            if (fechaNacimientoStr.matches("\\d+")) {
                try {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ofPattern("ddMMyyyy"));
                    fechaValida = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Fecha invalida. Por favor, ingrese la fecha nuevamente.");
                }
            } else {
                System.out.println("Fecha invalida. Debe contener solo numeros.");
                System.out.print("Ingrese la fecha de nacimiento nuevamente: ");
            }
        }



        // Crear carrito
        Carrito carrito = new Carrito();

        // Mostrar productos disponibles
        System.out.println("Productos disponibles:");
        List<Producto> inventario = stock.getInventario();
        for (int i = 0; i < inventario.size(); i++) {
            Producto producto = inventario.get(i);
            System.out.printf("%d. %s - $%.2f%n", (i + 1), producto.getNombre(), producto.getPrecio());
        }

        while (true) {
            System.out.println("\nOpciones:");
            System.out.println("1. Agregar producto al carrito");
            System.out.println("2. Eliminar producto del carrito");
            System.out.println("3. Mostrar carrito");
            System.out.println("4. Consultar categorias");
            System.out.println("5. Mostrar inventario");
            System.out.println("6. Pagar y generar ticket de compra");
            System.out.println("7. Salir");
            System.out.print("Ingrese el numero de opcion: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el numero de producto: ");
                    int numeroProducto = scanner.nextInt();
                    if (numeroProducto >= 1 && numeroProducto <= inventario.size()) {
                        Producto producto = inventario.get(numeroProducto - 1);
                        if (stock.obtenerCantidadDisponible(producto) > 0) {
                            carrito.agregarProducto(producto);
                            stock.actualizarStock(producto, 1);
                        } else {
                            System.out.println("No hay stock disponible para ese producto.");
                        }
                    } else {
                        System.out.println("Numero de producto invalido.");
                    }
                    break;
                case 2:
                    carrito.mostrarCarrito();
                    System.out.print("Ingrese el numero de producto a eliminar: ");
                    int numeroProductoEliminar = scanner.nextInt();
                    carrito.eliminarProducto(numeroProductoEliminar - 1);
                    break;
                case 3:
                    carrito.mostrarCarrito();
                    break;
                case 4:
                    System.out.println("Categorias de productos:");
                    for (CategoriaProducto categoria : CategoriaProducto.values()) {
                        System.out.println(categoria.toString());
                    }
                    break;
                case 5:
                    // Verificar credenciales
                    if (!dni.equals(login.getDni()) || !fechaNacimiento.equals(login.getFechaNacimiento())) {
                        System.out.println("Credenciales invalidas. No tiene permisos para realizar tal accion.");
                        scanner.next();
                    }
                    stock.mostrarInventario();
                    break;
                case 6:
                    Ticket ticket = new Ticket();
                    List<Producto> productos = carrito.getProductos();
                    for (Producto producto : productos) {
                        ticket.agregarProducto(producto);
                    }
                    ticket.generarTicket();
                    System.out.println("Gracias por su compra.");
                    System.exit(0);
                case 7:
                    System.out.println("Sesion finalizada.");
                    System.exit(0);
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        }
    }
}
