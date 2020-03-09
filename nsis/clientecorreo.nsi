# Instalador que nos pregunta donde queremos instalar
# Ruta por defecto: archivos de programa
# Incluye informacion sobre la desisntalacion

# Nombre del instalador
Name "Instalador del correoo"

# The file to write
OutFile "clientecorreo.exe"

# The default installation directory
InstallDir $DESKTOP\ClienteCorreo

# Pedimos permisos para Windows 7
RequestExecutionLevel admin

# Pantallas que hay que mostrar del instalador

Page directory
Page instfiles

#Seccion principal
Section

  # Establecemos el directorio de salida al directorio de instalacion
  SetOutPath $INSTDIR
  
  # Creamos el desinstalador
  writeUninstaller "$INSTDIR\uninstall.exe"
  
  # Ponemos ahi el archivo test.txt
  File /r "C:\Users\DAM\java\javafx-sdk-13"
  File /r "C:\Program Files\Java\java-runtime"
  File /r "C:\Users\DAM\IdeaProjects\eMailClientJavaFX\out\artifacts\eMailClientJavaFX_jar\"
  File /r "C:\Users\DAM\IdeaProjects\eMailClientJavaFX\help"
  File /r "C:\Users\DAM\IdeaProjects\eMailClientJavaFX\informes"
  
  # Creamos el acceso directo
  
  createShortCut "$DESKTOP\Cliente Correo.lnk" "$INSTDIR\java-runtime\bin\java.exe" "--module-path $INSTDIR\javafx-sdk-13\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base,javafx.web --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED --add-opens=javafx.graphics/com.sun.javafx.css=ALL-UNNAMED -jar $INSTDIR\eMailClientJavaFX.jar"
  
  createShortCut "$SMPROGRAMS\Cliente Correo.lnk" "$INSTDIR\java-runtime\bin\java.exe" "--module-path $INSTDIR\javafx-sdk-13\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base,javafx.web --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED --add-opens=javafx.graphics/com.sun.javafx.css=ALL-UNNAMED -jar $INSTDIR\eMailClientJavaFX.jar"
  
  #Añadimos información para que salga en el menú de desinstalar de Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ClienteCorreo" \
                 "DisplayName" "ClienteCorreo"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ClienteCorreo" \
                 "Publisher" "Javier - Desarrollo Interfaces"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ClienteCorreo" \
                 "UninstallString" "$\"$INSTDIR\uninstall.exe$\""
  
  messageBox MB_OK "Ha instalado el cliente de correo electronico"
  
# Fin de la seccion
SectionEnd

# seccion del desintalador
section "uninstall"
 
    # borramos el desintalador primero
    delete "$INSTDIR\uninstall.exe"
 
    # borramos el acceso directo del escritorio
    delete "$DESKTOP\Cliente Correo.lnk"
	
	# borramos el acceso directo del menu de inicio
	delete "$SMPROGRAMS\Cliente Correo.lnk"
	
	RmDir /r "$INSTDIR"
	
	#Borramos la entrada del registro
	DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ClienteCorreo"
 
# fin de la seccion del desinstalador
sectionEnd