#!/bin/bash

# Xenia-Droid Build Script
# Compilação automática do projeto

echo "========================================="
echo "Xenia-Droid - Script de Compilação"
echo "========================================="
echo ""

# Verificar se o gradlew existe
if [ ! -f "./gradlew" ]; then
    echo "❌ Erro: gradlew não encontrado!"
    echo "Execute este script no diretório raiz do projeto."
    exit 1
fi

# Dar permissão de execução ao gradlew
chmod +x ./gradlew

echo "📦 Verificando ambiente..."
echo ""

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "⚠️  Aviso: Java não encontrado no PATH"
    echo "   Certifique-se de ter o JDK 17 ou superior instalado"
fi

# Verificar se ANDROID_HOME está configurado
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  Aviso: ANDROID_HOME não está configurado"
    echo "   Defina a variável de ambiente ANDROID_HOME para o caminho do Android SDK"
fi

echo ""
echo "🔨 Iniciando compilação..."
echo ""

# Limpar build anterior
echo "🧹 Limpando builds anteriores..."
./gradlew clean

echo ""
echo "🏗️  Compilando APK Debug..."
echo ""

# Compilar APK Debug
./gradlew assembleDebug --stacktrace

# Verificar se a compilação foi bem-sucedida
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✅ Compilação concluída com sucesso!"
    echo "========================================="
    echo ""
    echo "📱 APK Debug gerado em:"
    echo "   app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "📦 Tamanho do APK:"
    ls -lh app/build/outputs/apk/debug/app-debug.apk | awk '{print "   "$5}'
    echo ""
    echo "🚀 Para instalar em um dispositivo conectado, execute:"
    echo "   ./gradlew installDebug"
    echo ""
    echo "   ou use adb diretamente:"
    echo "   adb install app/build/outputs/apk/debug/app-debug.apk"
    echo ""
else
    echo ""
    echo "========================================="
    echo "❌ Erro na compilação!"
    echo "========================================="
    echo ""
    echo "Verifique os erros acima e certifique-se de que:"
    echo "  1. Android SDK está instalado e ANDROID_HOME está configurado"
    echo "  2. Android NDK r26b está instalado"
    echo "  3. CMake 3.22.1+ está instalado"
    echo "  4. JDK 17 ou superior está instalado"
    echo ""
    exit 1
fi
