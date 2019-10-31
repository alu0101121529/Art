def factorial(n)
    if (n==0)
        return 1
    else
        return n*factorial(n - 1)
    end
end

puts "Introduce un número: "
num= gets.to_i
resultado = factorial(num)
puts "El resultado es #{resultado}"